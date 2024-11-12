package com.emc.belustyle.dto;

import com.emc.belustyle.entity.StockTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTransactionDTO {
    private Integer stockId;
    private Integer variationId;
    private String transactionType;
    private List<ProductVariationQuantity> variations;
    private String username;


    @Data
    public static class ProductVariationQuantity {
        private Integer productVariationId;
        private Integer quantity;
    }
}
