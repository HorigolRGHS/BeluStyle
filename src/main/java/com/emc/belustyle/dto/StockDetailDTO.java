package com.emc.belustyle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDetailDTO {
    private Integer stockId;
    private String stockName;
    private String stockAddress;
    private Integer variationId;
    private BigDecimal productPrice;
    private String productVariationImage;
    private String productName;
    private String productId;
    private Integer brandId;
    private String brandName;
    private Integer categoryId;
    private String categoryName;
    private String sizeName;
    private String colorName;
    private String hexCode;
    private Integer quantity;
}
