package com.emc.belustyle.dto;

import com.emc.belustyle.entity.ProductVariation;
import com.emc.belustyle.entity.Sale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private List<ProductVariationDTO> variations;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductVariationDTO {
        private Integer sizeId;
        private Integer colorId;
        private BigDecimal productPrice;
        private String productVariationImage;
    }
}
