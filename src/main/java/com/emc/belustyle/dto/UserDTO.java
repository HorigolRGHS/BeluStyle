package com.emc.belustyle.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String email;
    private String username;
    private String fullName;
    private String passwordHash; // or password in plain text
    private String userImage;
    private Boolean enable;
    private Integer roleId;
    private String currentPaymentMethod;
    private String userAddress;

}


