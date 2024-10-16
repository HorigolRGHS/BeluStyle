package com.emc.belustyle.dto;

import com.emc.belustyle.entity.StockTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTransactionDTO {
    private Integer stockId;
    private Integer variationId;
    private String transactionType;
    private Integer quantity;
}
