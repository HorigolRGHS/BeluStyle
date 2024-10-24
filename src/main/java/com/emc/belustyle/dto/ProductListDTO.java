package com.emc.belustyle.dto;

import com.emc.belustyle.entity.Sale;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductListDTO {
    private String productId;
    private String productName;
    private String productDescription;
    private String brandName;
    private String categoryName;
    private String productVariationImage;
    private BigDecimal productPrice;
    private Sale.SaleType saleType;
    private BigDecimal saleValue;
    private Double averageRating;
    private Long totalRatings;
}