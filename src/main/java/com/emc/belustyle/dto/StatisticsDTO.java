package com.emc.belustyle.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class StatisticsDTO {
    private BigDecimal totalRevenue;
    private Map<String, Long> orderStatus; // Key: Status, Value: Count
    private List<ProductStatisticsDTO> bestSellingProducts;

    public StatisticsDTO(BigDecimal totalRevenue, Map<String, Long> orderStatus, List<ProductStatisticsDTO> bestSellingProducts) {
        this.totalRevenue = totalRevenue;
        this.orderStatus = orderStatus;
        this.bestSellingProducts = bestSellingProducts;
    }

    // Getters and Setters
}
