package com.emc.belustyle.dto;

import com.emc.belustyle.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String email;
    private String username;
    private String fullName;
    private String passwordHash;
    private String userImage;
    private Boolean enable;
    private Integer roleId;
    private String roleName;
    private String currentPaymentMethod;
    private String userAddress;


    public UserDTO() {
    }
}

