package com.emc.belustyle.dto;

import com.emc.belustyle.entity.Color;
import com.emc.belustyle.entity.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductVariationCheckDTO {
    private int variationId;
    private Size size;
    private Color color;
    private BigDecimal price;
    private String stock;

    public ProductVariationCheckDTO(int variationId, Size size, Color color, BigDecimal price, String stock) {
        this.variationId = variationId;
        this.size = size;
        this.color = color;
        this.price = price;
        this.stock = stock;
    }
}
