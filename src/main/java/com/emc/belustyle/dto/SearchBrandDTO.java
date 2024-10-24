package com.emc.belustyle.dto;

import lombok.Data;

@Data
public class SearchBrandDTO {
    private String brandName;
    private String brandDescription;

    public SearchBrandDTO(String brandName, String brandDescription) {
        this.brandName = brandName;
        this.brandDescription = brandDescription;
    }
}
