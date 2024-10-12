package com.emc.belustyle.dto;

import com.emc.belustyle.entity.Sale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {

    private Sale.SaleType saleType;
    private BigDecimal saleValue;
    private Date startDate;
    private Date endDate;
    private Sale.SaleStatus saleStatus;
    private List<String> productIds;
}
