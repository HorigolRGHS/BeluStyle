package com.emc.belustyle.dto;

import com.emc.belustyle.entity.Sale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class SaleDTO {
    private int saleId;
    private Sale.SaleType saleType;
    private BigDecimal saleValue;
    private Sale.SaleStatus saleStatus;
    private Date startDate;
    private Date endDate;
    private Date createdAt;
    private Date updatedAt;
    private List<ProductDTO> products;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductDTO {
        private String productId;
        private String productName;
        private BigDecimal productPrice;

    }
}
