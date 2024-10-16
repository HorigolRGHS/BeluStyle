package com.emc.belustyle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
    private Integer orderDetailId;
    private String orderId;
    private Integer variationId;
    private Integer orderQuantity;
    private BigDecimal unitPrice;
    private BigDecimal discountAmount;
}
