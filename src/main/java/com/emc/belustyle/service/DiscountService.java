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
import java.text.DecimalFormat;
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
        // Kiểm tra ngày bắt đầu và kết thúc
        if (discountDTO.getStartDate() != null && discountDTO.getEndDate() != null &&
                discountDTO.getStartDate().after(discountDTO.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        // Đặt usage_limit mặc định nếu không được cung cấp
        if (discountDTO.getUsageLimit() == null) {
            discountDTO.setUsageLimit(Integer.MAX_VALUE); // Không giới hạn
        }

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
        if (!discountOpt.isPresent()) {
            throw new IllegalArgumentException("Discount code not found.");
        }

        Discount discount = discountOpt.get();

        // Kiểm tra nếu discount đã được sử dụng hết
        if (discount.getUsageLimit() == 0) {
            throw new IllegalStateException("This discount has been fully used by all users.");
        }

        // Kiểm tra số lần sử dụng của user cụ thể
        return userDiscountRepository
                .findByUser_UserIdAndDiscount_DiscountId(userId, discount.getDiscountId())
                .map(UserDiscount::getUsageCount)
                .orElse(0); // Mặc định trả về 0 nếu user chưa từng sử dụng
    }


    @Transactional
    public void addUsersToDiscount(List<String> userIds, Integer discountId) {
        Optional<Discount> discountOpt = discountRepository.findById(discountId);
        if (!discountOpt.isPresent()) {
            throw new ResourceNotFoundException("Discount not found.");
        }

        Discount discount = discountOpt.get();
        List<UserDiscount> userDiscounts = new ArrayList<>();

        for (String userId : userIds) {
            if (!userDiscountRepository.existsByUser_UserIdAndDiscount_DiscountId(userId, discountId)) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
                UserDiscount userDiscount = new UserDiscount(user, discount);
                userDiscount.setUsageCount(1); // Mặc định 1 lần sử dụng
                userDiscounts.add(userDiscount);
            }
        }

        if (!userDiscounts.isEmpty()) {
            userDiscountRepository.saveAll(userDiscounts);
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
                    Discount discount = discountRepository.findById(userDiscount.getId().getDiscountId())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid discount ID"));

                    // Kiểm tra trạng thái và trả về DTO
                    if (discount.getDiscountStatus() == Discount.DiscountStatus.EXPIRED ||
                            discount.getDiscountStatus() == Discount.DiscountStatus.USED) {
                        return null; // Bỏ qua các discount không hợp lệ
                    }

                    return convertToDTO(discount);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    public List<DiscountDTO> getDiscountsByUsername(String username) {
        // Lấy danh sách UserDiscount liên kết với username
        List<UserDiscount> userDiscounts = userDiscountRepository.findAllByUser_Username(username);

        return userDiscounts.stream()
                .map(userDiscount -> {
                    // Tìm Discount tương ứng
                    Optional<Discount> discountOpt = discountRepository.findById(userDiscount.getId().getDiscountId());
                    if (discountOpt.isPresent()) {
                        Discount discount = discountOpt.get();
                        DiscountDTO discountDTO = convertToDTO(discount);

                        // Gán usageCount chỉ cho người dùng cụ thể
                        discountDTO.setUsageCount(userDiscount.getUsageCount());

                        return discountDTO;
                    }
                    return null;
                })
                .filter(Objects::nonNull) // Loại bỏ các giá trị null (nếu có lỗi khi tìm discount)
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
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
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
            if (Objects.equals(discountDTO.getDiscountStatus(), "USED")) {
                return ResponseEntity.status(HttpStatus.GONE).body(new ResponseDTO(HttpStatus.GONE.value(), "Discount has reached the limit! Try another discount."));
            }
            if (discountDTO.getMinimumOrderValue() != null && discountDTO.getMinimumOrderValue().compareTo(subTotal) <= 0) {
                return ResponseEntity.ok(discountDTO);
            } else if (discountDTO.getMinimumOrderValue() == null) {
                return ResponseEntity.ok(discountDTO);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "The minimum order value is " + decimalFormat.format(discountDTO.getMinimumOrderValue()) + ". Please add more items to your cart.")
                );
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(HttpStatus.NOT_FOUND.value(), "User does not have this discount"));
        }
    }

    @Transactional
    public void assignNewUserDiscount(String userId) {
  
        Optional<Discount> newUserDiscountOpt = discountRepository.findByDiscountCode("BELUSTYLEGIFT");

        if (newUserDiscountOpt.isPresent()) {
            Discount discount = newUserDiscountOpt.get();


            Optional<UserDiscount> existingUserDiscount = userDiscountRepository
                    .findByUser_UserIdAndDiscount_DiscountId(userId, discount.getDiscountId());

            if (!existingUserDiscount.isPresent()) {

                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

                UserDiscount userDiscount = new UserDiscount();
                userDiscount.setId(new UserDiscount.UserDiscountId(userId, discount.getDiscountId()));
                userDiscount.setUser(user);
                userDiscount.setDiscount(discount);

                userDiscount.setUsageCount(1);

                userDiscountRepository.save(userDiscount);

                discount.setUsageLimit((discount.getUsageLimit() == null ? 0 : discount.getUsageLimit()) + 1);

                discountRepository.save(discount);
            }
        } else {
            throw new ResourceNotFoundException("Default discount BELUSTYLEGIFT not found.");
        }
    }


    @Transactional
    public void updateUsageLimit(String discountCode, String userId) {
        // Tìm mã giảm giá
        Optional<Discount> discountOpt = discountRepository.findByDiscountCode(discountCode);
        if (!discountOpt.isPresent()) {
            throw new IllegalArgumentException("Discount code not found.");
        }

        Discount discount = discountOpt.get();

        // Kiểm tra nếu `usage_limit` của mã giảm giá đã hết
        if (discount.getUsageLimit() <= 0) {
            throw new IllegalStateException("This discount has been fully used.");
        }

        // Kiểm tra `UserDiscount` của người dùng hiện tại
        UserDiscount userDiscount = userDiscountRepository
                .findByUser_UserIdAndDiscount_DiscountId(userId, discount.getDiscountId())
                .orElseThrow(() -> new IllegalStateException("User has not been assigned this discount."));

        // Kiểm tra nếu `usage_count` của người dùng đã hết
        if (userDiscount.getUsageCount() <= 0) {
            throw new IllegalStateException("User has already used this discount.");
        }

        // Giảm `usage_count` của người dùng
        userDiscount.setUsageCount(userDiscount.getUsageCount() - 1);
        userDiscount.setUsedAt(new Date());
        userDiscountRepository.save(userDiscount);

        // Giảm `usage_limit` tổng quan của mã giảm giá
        if (discount.getUsageLimit() > 0) {
            discount.setUsageLimit(discount.getUsageLimit() - 1);
            if (discount.getUsageLimit() == 0) {
                discount.setDiscountStatus(Discount.DiscountStatus.USED);
            }
        }

        discountRepository.save(discount);
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

        // Tính toán tổng số lần sử dụng
        int totalUsageCount = userDiscountRepository.countByDiscountId(discount.getDiscountId());
        discountDTO.setUsageCount(totalUsageCount);

        // Cập nhật trạng thái nếu đạt giới hạn
        if (discount.getUsageLimit() != null && totalUsageCount >= discount.getUsageLimit()) {
            discount.setDiscountStatus(Discount.DiscountStatus.USED);
            discountRepository.save(discount); // Lưu trạng thái mới
        }

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