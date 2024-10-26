package com.emc.belustyle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountUserDTO {
    private Integer userDiscountId;
    private Integer discountId;
    private String userId;
    private Integer usageCount;
    private Date usedAt;
}
