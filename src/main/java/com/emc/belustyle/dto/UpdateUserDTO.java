package com.emc.belustyle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UpdateUserDTO{
    private String userId;
    private String fullName;
    private String userImage;
    private String currentPaymentMethod;
    private String userAddress;
}
