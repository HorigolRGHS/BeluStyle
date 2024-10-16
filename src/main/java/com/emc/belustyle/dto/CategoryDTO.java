package com.emc.belustyle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private int categoryId;
    private String categoryName;
    private String categoryDescription;
    private String imageUrl;
    private Date createdAt;
    private Date updatedAt;
    private Long totalQuantity; // Use Long for quantity since it can be large
}
