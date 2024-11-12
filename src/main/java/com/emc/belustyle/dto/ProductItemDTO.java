package com.emc.belustyle.dto;

import com.emc.belustyle.entity.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Data
@NoArgsConstructor
public class ProductItemDTO {
    private String productId;
    private String productName;
    private Map<String, Map<String, ProductVariationDTO>> variations;
    private List<Color> colors;
    private List<Size> sizes;
    private Sale.SaleType saleType;
    private BigDecimal saleValue;
    private String description;
    private double avgRating;
    private long totalRating;
    private List<ProductReviewDTO> reviews;
}
