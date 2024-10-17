package com.emc.belustyle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {
    private int brandId;
    private String brandName;
    private String brandDescription;
    private String logoUrl;
    private String websiteUrl;
    private Date createdAt;
    private Date updatedAt;
    private Long totalQuantity;  // Use Long for quantity since it can be large
}
