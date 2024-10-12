package com.emc.belustyle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
    private int stockId;
    private String stockName;
    private String stockAddress;
}
