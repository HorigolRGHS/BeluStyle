package com.emc.belustyle.dto;

import com.emc.belustyle.entity.ProductVariation;
import com.emc.belustyle.entity.Sale;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ProductDTO {
    private String productId;
    private String productName;
    private Integer categoryId;
    private Integer brandId;
    private String productDescription;
    private Date createdAt;
    private Date updatedAt;
    private BigDecimal productPrice;
    private Sale sale;
    private List<ProductVariation> listProductVariation;

}
