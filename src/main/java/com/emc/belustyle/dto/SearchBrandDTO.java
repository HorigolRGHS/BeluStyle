package com.emc.belustyle.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchBrandDTO {
    private String brandName;
    private String brandDescription;

    public SearchBrandDTO(String brandName, String brandDescription) {
        this.brandName = brandName;
        this.brandDescription = brandDescription;
    }
}
