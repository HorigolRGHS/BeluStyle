package com.emc.belustyle.service;

import com.emc.belustyle.dto.DiscountDTO;
import com.emc.belustyle.dto.DiscountUserViewDTO;
import com.emc.belustyle.dto.ResponseDTO;
import com.emc.belustyle.dto.UserDTO;
import com.emc.belustyle.entity.Discount;
import com.emc.belustyle.entity.User;
import com.emc.belustyle.entity.UserDiscount;
import com.emc.belustyle.entity.UserDiscount.UserDiscountId;
import com.emc.belustyle.repo.UserRepository;
import com.emc.belustyle.dto.mapper.UserMapper;
import com.emc.belustyle.repo.DiscountRepository;
import com.emc.belustyle.repo.UserDiscountRepository;
import com.emc.belustyle.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.time.ZoneId;
import java.util.stream.Collectors;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final UserDiscountRepository userDiscountRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;


    @Autowired
    public DiscountService(DiscountRepository discountRepository,
                           UserDiscountRepository userDiscountRepository,
                           UserRepository userRepository,
                           JwtUtil jwtUtil,
                           UserMapper userMapper) {
        this.discountRepository = discountRepository;
        this.userDiscountRepository = userDiscountRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
    }


    public Page<DiscountDTO> getAllDiscounts(int page, int size) {
        return discountRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "discountId"))
        ).map(this::convertToDTO);
    }

    public Optional<DiscountDTO> getDiscountById(int discountId) {
        return discountRepository.findById(discountId).map(this::convertToDTO);
    }

    @Transactional
    public DiscountDTO addDiscount(DiscountDTO discountDTO) {
        Discount discount = convertToEntity(discountDTO);
        return convertToDTO(discountRepository.save(discount));
    }

    @Transactional
    public Optional<DiscountDTO> updateDiscount(Integer discountId, DiscountDTO discountDTO) {
        Optional<Discount> discountOpt = discountRepository.findById(discountId);

        if (discountOpt.isPresent()) {
            Discount updatedDiscount = convertToEntity(discountDTO);
            updatedDiscount.setDiscountId(discountId);

            // Cập nhật createdAt chỉ khi cần thiết
            if (discountOpt.get().getCreatedAt() != null) {
                updatedDiscount.setCreatedAt(discountOpt.get().getCreatedAt());
            }

            // Thiết lập updatedAt là thời điểm hiện tại
            updatedDiscount.setUpdatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

            return Optional.of(convertToDTO(discountRepository.save(updatedDiscount)));
        }
        return Optional.empty();
    }

    public Optional<DiscountDTO> findDiscountByCode(String discountCode) {
        return discountRepository.findByDiscountCode(discountCode).map(this::convertToDTO);
    }

    public Optional<DiscountDTO> endDiscount(int discountId) {
        Optional<Discount> discount = discountRepository.findById(discountId);
        if (discount.isPresent()) {
            discount.get().setDiscountStatus(Discount.DiscountStatus.EXPIRED);
            return Optional.of(convertToDTO(discountRepository.save(discount.get())));
        }
        return Optional.empty();
    }
    @Transactional
    public void deleteDiscount(int discountId) {
        discountRepository.deleteById(discountId);
    }

    public int checkDiscountUsage(String userId, String discountCode) {
        Optional<Discount> discountOpt = discountRepository.findByDiscountCode(discountCode);
        if (discountOpt.isPresent()) {
            int discountId = discountOpt.get().getDiscountId();
            List<UserDiscount> userDiscounts = userDiscountRepository.findAllByDiscount_DiscountId(discountId);
            return (int) userDiscounts.stream()
                    .filter(userDiscount -> userDiscount.getId().getUserId().equals(userId))
                    .count();
        }
        return 0;
    }

    @Transactional
    public void addUsersToDiscount(List<String> userIds, Integer discountId) {
        Optional<Discount> discountOpt = discountRepository.findById(discountId);
        if (discountOpt.isPresent()) {
            Discount discount = discountOpt.get();
            List<UserDiscount> userDiscounts = new ArrayList<>();

            for (String userId : userIds) {
                // Create UserDiscountId object using userId and discountId
                UserDiscountId userDiscountId = new UserDiscountId(userId, discountId);

                // Check if the relationship between user and discount already exists
                Optional<UserDiscount> existingUserDiscount = userDiscountRepository.findByUser_UserIdAndDiscount_DiscountId(userId, discountId);
                if (!existingUserDiscount.isPresent()) {
                    UserDiscount userDiscount = new UserDiscount();
                    userDiscount.setId(userDiscountId);
                    userDiscount.setUser(userRepository.findById(userId).orElse(null));  // Assuming 'user' is part of the discount, or set it correctly
                    userDiscount.setDiscount(discount);       // Associate the discount
                    userDiscount.setUsageCount(0);

                    userDiscounts.add(userDiscount);  // Collect all userDiscounts for batch saving
                }
            }

            // Save all userDiscounts in bulk at once for better performance
            if (!userDiscounts.isEmpty()) {
                userDiscountRepository.saveAll(userDiscounts);
            }
        } else {
            throw new ResourceNotFoundException("Discount not found.");
        }
    }


    @Transactional
    public void removeUserFromDiscount(String userId, Integer discountId) {
        UserDiscountId userDiscountId = new UserDiscountId(userId, discountId);
        Optional<UserDiscount> userDiscountOpt = userDiscountRepository.findByUser_UserIdAndDiscount_DiscountId(userId, discountId);
        userDiscountOpt.ifPresent(userDiscountRepository::delete);
    }

    public List<DiscountDTO> getDiscountsByUserId(String userId) {
        List<UserDiscount> userDiscounts = userDiscountRepository.findAllByUser_UserId(userId);
        return userDiscounts.stream()
                .map(userDiscount -> {
                    Optional<Discount> discountOpt = discountRepository.findById(userDiscount.getId().getDiscountId());
                    return discountOpt.map(this::convertToDTO).orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<DiscountDTO> getDiscountsByUsername(String username) {
        List<UserDiscount> userDiscounts = userDiscountRepository.findAllByUser_Username(username);
        return userDiscounts.stream()
                .map(userDiscount -> {
                    Optional<Discount> discountOpt = discountRepository.findById(userDiscount.getId().getDiscountId());
                    return discountOpt.map(this::convertToDTO).orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    public Map<String, Object> getUsersByDiscountId(Integer discountId) {
        List<UserDiscount> userDiscounts = userDiscountRepository.findAllByDiscount_DiscountId(discountId).stream().toList();

        List<DiscountUserViewDTO> users = userDiscounts.stream()
                .map(userDiscount -> {
                    User user = userRepository.findById(userDiscount.getId().getUserId()).orElse(null);
                    if (user != null) {
                        return new DiscountUserViewDTO(
                                user.getUserId(),
                                user.getUsername(),
                                user.getEmail(),
                                user.getFullName(),
                                user.getUserImage()
                        );
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("users", users);
        result.put("userDiscounts", userDiscounts);

        return result;
    }


    public ResponseEntity<?> checkUserDiscount(String discountCode, BigDecimal subTotal) {
        System.out.println(subTotal);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            currentUsername = ((UserDetails) authentication.getPrincipal()).getUsername();
        }

        if (currentUsername == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(HttpStatus.UNAUTHORIZED.value(), "User not authenticated"));
        }

        String userId = userRepository.findByUsername(currentUsername)
                .map(User::getUserId)
                .orElse(null);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(HttpStatus.NOT_FOUND.value(), "User ID not found for authenticated user"));
        }

        Optional<DiscountDTO> discountOpt = findDiscountByCode(discountCode);
        if (!discountOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(HttpStatus.NOT_FOUND.value(), "Discount code not found"));
        }

        boolean hasDiscount = checkDiscountUsage(userId, discountCode) > 0;
        if (hasDiscount) {
            DiscountDTO discountDTO = discountOpt.get();
            if (Objects.equals(discountDTO.getDiscountStatus(), "EXPIRED")) {
                return ResponseEntity.status(HttpStatus.GONE).body(new ResponseDTO(HttpStatus.GONE.value(), "Discount expired"));
            }
            if (discountDTO.getMinimumOrderValue().compareTo(subTotal) <= 0) {
                return ResponseEntity.ok(discountDTO);
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Minimum Order Value is " + discountDTO.getMinimumOrderValue()));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(HttpStatus.NOT_FOUND.value(), "User does not have this discount"));
        }
    }

    private DiscountDTO convertToDTO(Discount discount) {
        DiscountDTO discountDTO = new DiscountDTO(
                discount.getDiscountId(),
                discount.getDiscountCode(),
                discount.getDiscountType().name(),
                discount.getDiscountValue(),
                discount.getStartDate(),
                discount.getEndDate(),
                discount.getDiscountStatus().name(),
                discount.getDiscountDescription(),
                discount.getMinimumOrderValue(),
                discount.getMaximumDiscountValue(),
                discount.getUsageLimit()
        );

        // Calculate the usage count
        int usageCount = userDiscountRepository.findAllByDiscount_DiscountId(discount.getDiscountId()).size();
        discountDTO.setUsageCount(usageCount); // Set the usage count

        return discountDTO;
    }


    private Discount convertToEntity(DiscountDTO discountDTO) {
        Discount discount = new Discount();
        discount.setDiscountCode(discountDTO.getDiscountCode());
        discount.setDiscountType(Discount.DiscountType.valueOf(discountDTO.getDiscountType()));
        discount.setDiscountValue(discountDTO.getDiscountValue());
        discount.setStartDate(discountDTO.getStartDate());
        discount.setEndDate(discountDTO.getEndDate());
        discount.setDiscountStatus(Discount.DiscountStatus.valueOf(discountDTO.getDiscountStatus()));
        discount.setDiscountDescription(discountDTO.getDiscountDescription());
        discount.setMinimumOrderValue(discountDTO.getMinimumOrderValue());
        discount.setMaximumDiscountValue(discountDTO.getMaximumDiscountValue());
        discount.setUsageLimit(discountDTO.getUsageLimit());

        return discount;
    }
}