package com.emc.belustyle.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
public class MonthlyRevenueDTO {
    private int year;
    private int month;
    private BigDecimal totalAmount;

    public MonthlyRevenueDTO(int year, int month, BigDecimal totalAmount) {
        this.year = year;
        this.month = month;
        this.totalAmount = totalAmount;
    }

    // Getter và Setter
}