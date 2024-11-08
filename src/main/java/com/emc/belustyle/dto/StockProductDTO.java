package com.emc.belustyle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockProductDTO {
    private int stockId;
    private List<ProductVariationQuantity> variations;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductVariationQuantity {
        private int variationId;
        private int quantity;

    }
}
