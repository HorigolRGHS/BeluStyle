package com.emc.belustyle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO {
    private Integer discountId;
    private String discountCode;
    private String discountType;    // Loại giảm giá: PERCENTAGE, FIXED_AMOUNT
    private BigDecimal discountValue;
    private Date startDate;
    private Date endDate;
    private String discountStatus;  // Trạng thái: ACTIVE, EXPIRED, USED
    private String discountDescription;
    private BigDecimal minimumOrderValue;  // Giá trị đơn hàng tối thiểu
    private BigDecimal maximumDiscountValue;  // Giảm giá tối đa
    private Integer usageLimit;    // Giới hạn sử dụng
}
