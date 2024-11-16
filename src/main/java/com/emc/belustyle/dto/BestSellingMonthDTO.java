package com.emc.belustyle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BestSellingMonthDTO {
    private String productId;
    private String productName;
    private int totalQuantitySold;
}
