package com.emc.belustyle.dto;

import com.emc.belustyle.entity.Brand;
import com.emc.belustyle.entity.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
