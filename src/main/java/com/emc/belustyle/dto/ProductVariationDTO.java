package com.emc.belustyle.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductVariationDTO {
    private int id;
    private BigDecimal price;
    private String images;
    private long quantity;
}
