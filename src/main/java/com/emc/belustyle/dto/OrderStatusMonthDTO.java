package com.emc.belustyle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusMonthDTO {
    private String orderStatus;
    private int totalOrders;
}
