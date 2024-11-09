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
    private String discountType;   // Loại giảm giá: PERCENTAGE, FIXED_AMOUNT
    private BigDecimal discountValue;
    private Date startDate;
    private Date endDate;
    private String discountStatus; // Trạng thái: ACTIVE, EXPIRED, USED
    private String discountDescription;
    private BigDecimal minimumOrderValue;
    private BigDecimal maximumDiscountValue;
    private Integer usageLimit;

    private Integer usageCount;

    public DiscountDTO(Integer discountId, String discountCode, String discountType, BigDecimal discountValue,
                       Date startDate, Date endDate, String discountStatus, String discountDescription,
                       BigDecimal minimumOrderValue, BigDecimal maximumDiscountValue, Integer usageCount) {
        this.discountId = discountId;
        this.discountCode = discountCode;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountStatus = discountStatus;
        this.discountDescription = discountDescription;
        this.minimumOrderValue = minimumOrderValue;
        this.maximumDiscountValue = maximumDiscountValue;
        this.usageCount = usageCount;
    }

}
