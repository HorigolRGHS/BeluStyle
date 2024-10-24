package com.emc.belustyle.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDTO {
    private String productId;
    private String productName;
    private Integer categoryId;
    private Integer brandId;
    private String productDescription;
    private Date createdAt;
    private Date updatedAt;

}
