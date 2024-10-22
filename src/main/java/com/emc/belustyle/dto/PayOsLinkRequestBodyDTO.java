package com.emc.belustyle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PayOsLinkRequestBodyDTO {
    private String productName;
    private String description;
    private String returnUrl;
    private int price;
    private String cancelUrl;
    private List<ItemDataDTO> items;

}
