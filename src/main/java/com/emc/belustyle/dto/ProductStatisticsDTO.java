package com.emc.belustyle.dto;

import com.emc.belustyle.entity.ProductVariation;
import com.emc.belustyle.entity.Sale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductStatisticsDTO {
    private String productId;
    private String productName;
    private int totalSold;

    public ProductStatisticsDTO(String productId, String productName, int totalSold) {
        this.productId = productId;
        this.productName = productName;
        this.totalSold = totalSold;
    }

    // Getters and Setters
}
