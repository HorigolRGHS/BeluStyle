package com.emc.belustyle.service;

import com.emc.belustyle.dto.DiscountDTO;
import com.emc.belustyle.dto.DiscountUserViewDTO;
import com.emc.belustyle.dto.UserDTO;
import com.emc.belustyle.entity.Discount;
import com.emc.belustyle.entity.User;
import com.emc.belustyle.entity.UserDiscount;
import com.emc.belustyle.repo.UserRepository;
import com.emc.belustyle.dto.mapper.UserMapper;
import com.emc.belustyle.repo.DiscountRepository;
import com.emc.belustyle.repo.UserDiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.time.ZoneId; // Giữ nguyên
import java.util.stream.Collectors;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final UserDiscountRepository userDiscountRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public DiscountService(DiscountRepository discountRepository,
                           UserDiscountRepository userDiscountRepository,
                           UserRepository userRepository,
                           UserMapper userMapper) {
        this.discountRepository = discountRepository;
        this.userDiscountRepository = userDiscountRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
        // Tìm mã giảm giá theo discountCode
        Optional<Discount> discountOpt = discountRepository.findByDiscountCode(discountCode);
        if (discountOpt.isPresent()) {
            int discountId = discountOpt.get().getDiscountId(); // Lấy ID của mã giảm giá
            List<UserDiscount> userDiscounts = userDiscountRepository.findAllByDiscountId(discountId);
            // Lọc danh sách để đếm số lần sử dụng của userId
            return (int) userDiscounts.stream()
                    .filter(userDiscount -> userDiscount.getUserId().equals(userId))
                    .count(); // Trả về số lần sử dụng mã giảm giá
        }
        return 0; // Trả về 0 nếu không tìm thấy mã giảm giá
    }

    @Transactional
    public void addUsersToDiscount(List<String> userIds, Integer discountId) {
        Optional<Discount> discountOpt = discountRepository.findById(discountId);
        if (discountOpt.isPresent()) {
            for (String userId : userIds) {
                UserDiscount userDiscount = new UserDiscount();
                userDiscount.setUserId(userId);
                userDiscount.setDiscountId(discountId);
                userDiscount.setUsageCount(0); // Thiết lập giá trị mặc định cho usageCount
                userDiscountRepository.save(userDiscount);
            }
        } else {
            throw new IllegalArgumentException("Discount not found.");
        }
    }



    @Transactional
    public void removeUserFromDiscount(String userId, Integer discountId) {
        Optional<UserDiscount> userDiscountOpt = userDiscountRepository.findByUserIdAndDiscountId(userId, discountId);
        userDiscountOpt.ifPresent(userDiscountRepository::delete);
    }


    public List<DiscountDTO> getDiscountsByUserId(String userId) {
        List<UserDiscount> userDiscounts = userDiscountRepository.findAllByUserId(userId);
        return userDiscounts.stream()
                .map(userDiscount -> {
                    Optional<Discount> discountOpt = discountRepository.findById(userDiscount.getDiscountId());
                    return discountOpt.map(this::convertToDTO).orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Map<String, Object> getUsersByDiscountId(Integer discountId) {
        // Fetch the list of UserDiscount entities by discountId
        List<UserDiscount> userDiscounts = userDiscountRepository.findAllByDiscountId(discountId);

        // Map the associated Users to UserDiscountViewDTO objects
        List<DiscountUserViewDTO> users = userDiscounts.stream()
                .map(userDiscount -> {
                    User user = userRepository.findById(userDiscount.getUserId()).orElse(null);
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

        // Prepare the response map with filtered users and userDiscounts
        Map<String, Object> result = new HashMap<>();
        result.put("users", users);
        result.put("userDiscounts", userDiscounts);

        return result;
    }





    // Hàm chuyển đổi từ entity sang DTO
    private DiscountDTO convertToDTO(Discount discount) {
        return new DiscountDTO(
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
    }

    // Hàm chuyển đổi từ DTO sang entity
    private Discount convertToEntity(DiscountDTO discountDTO) {
        Discount discount = new Discount();
        discount.setDiscountCode(discountDTO.getDiscountCode());
        discount.setDiscountType(Discount.DiscountType.valueOf(discountDTO.getDiscountType())); // Sửa lại tham chiếu DiscountType
        discount.setDiscountValue(discountDTO.getDiscountValue());
        discount.setStartDate(discountDTO.getStartDate());
        discount.setEndDate(discountDTO.getEndDate());
        discount.setDiscountStatus(Discount.DiscountStatus.valueOf(discountDTO.getDiscountStatus())); // Sửa lại tham chiếu DiscountStatus
        discount.setDiscountDescription(discountDTO.getDiscountDescription());
        discount.setMinimumOrderValue(discountDTO.getMinimumOrderValue());
        discount.setMaximumDiscountValue(discountDTO.getMaximumDiscountValue());
        discount.setUsageLimit(discountDTO.getUsageLimit());

        return discount;
    }
}
