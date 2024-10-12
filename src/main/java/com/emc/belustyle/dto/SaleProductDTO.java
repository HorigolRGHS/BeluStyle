package com.emc.belustyle.dto;


import com.emc.belustyle.entity.Sale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleProductDTO {
    private Sale.SaleType saleType;
    private BigDecimal saleValue;
    private Date startDate;
    private Date endDate;
    private Sale.SaleStatus saleStatus;
    private List<Integer> productIds;
}
