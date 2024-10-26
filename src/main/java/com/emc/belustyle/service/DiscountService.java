package com.emc.belustyle.service;

import com.emc.belustyle.dto.DiscountDTO;
import com.emc.belustyle.entity.Discount;
import com.emc.belustyle.entity.Discount.DiscountStatus;
import com.emc.belustyle.entity.Discount.DiscountType;
import com.emc.belustyle.repo.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
import java.util.Date;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;

    @Autowired
    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    // Thêm mới discount
    @Transactional
    public DiscountDTO addDiscount(DiscountDTO discountDTO) {
        // Kiểm tra xem discount code đã tồn tại chưa
        Optional<Discount> existingDiscount = discountRepository.findByDiscountCode(discountDTO.getDiscountCode());
        if (existingDiscount.isPresent()) {
            throw new IllegalArgumentException("Discount code already exists."); // Ném ngoại lệ nếu discount code đã tồn tại
        }

        Discount discount = convertToEntity(discountDTO);
        discount.setDiscountStatus(DiscountStatus.ACTIVE);

        // Thiết lập thời gian tạo cho createdAt
        discount.setCreatedAt(new Date()); // Hoặc sử dụng LocalDateTime.now() nếu bạn thích

        Discount savedDiscount = discountRepository.save(discount);
        return convertToDTO(savedDiscount);
    }

    // Lấy discount theo ID
    public Optional<DiscountDTO> getDiscountById(int discountId) {
        return discountRepository.findById(discountId)
                .map(this::convertToDTO);
    }
    public Optional<DiscountDTO> findDiscountByCode(String discountCode) {
        return discountRepository.findByDiscountCode(discountCode) // Giả định bạn đã định nghĩa phương thức này trong DiscountRepository
                .map(this::convertToDTO);
    }

    // Cập nhật discount
    @Transactional
    public Optional<DiscountDTO> updateDiscount(int discountId, DiscountDTO discountDTO) {
        return discountRepository.findById(discountId).map(discount -> {
            discount.setDiscountCode(discountDTO.getDiscountCode());
            discount.setDiscountType(DiscountType.valueOf(discountDTO.getDiscountType()));
            discount.setDiscountValue(discountDTO.getDiscountValue());
            discount.setStartDate(discountDTO.getStartDate());
            discount.setEndDate(discountDTO.getEndDate());
            discount.setDiscountStatus(DiscountStatus.valueOf(discountDTO.getDiscountStatus()));
            discount.setDiscountDescription(discountDTO.getDiscountDescription());
            discount.setMinimumOrderValue(discountDTO.getMinimumOrderValue());
            discount.setMaximumDiscountValue(discountDTO.getMaximumDiscountValue());
            discount.setUsageLimit(discountDTO.getUsageLimit());
            Discount updatedDiscount = discountRepository.save(discount);
            return convertToDTO(updatedDiscount);
        });
    }

    // Lấy danh sách discount với phân trang
    public Page<DiscountDTO> getAllDiscounts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "discountId"));
        return discountRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    // Kết thúc discount
    @Transactional
    public Optional<DiscountDTO> endDiscount(int discountId) {
        return discountRepository.findById(discountId).map(discount -> {
            discount.setDiscountStatus(DiscountStatus.EXPIRED);
            Discount endedDiscount = discountRepository.save(discount);
            return convertToDTO(endedDiscount);
        });
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
        discount.setDiscountType(DiscountType.valueOf(discountDTO.getDiscountType()));
        discount.setDiscountValue(discountDTO.getDiscountValue());
        discount.setStartDate(discountDTO.getStartDate());
        discount.setEndDate(discountDTO.getEndDate());
        discount.setDiscountStatus(DiscountStatus.valueOf(discountDTO.getDiscountStatus()));
        discount.setDiscountDescription(discountDTO.getDiscountDescription());
        discount.setMinimumOrderValue(discountDTO.getMinimumOrderValue());
        discount.setMaximumDiscountValue(discountDTO.getMaximumDiscountValue());
        discount.setUsageLimit(discountDTO.getUsageLimit());


        System.out.printf(discount.toString()   );
        return discount;
    }


    @Transactional
    public boolean deleteDiscount(int discountId) {
        if (discountRepository.existsById(discountId)) {
            discountRepository.deleteById(discountId);
            return true; // Trả về true nếu xóa thành công
        }
        return false; // Trả về false nếu không tìm thấy Discount
    }

}
