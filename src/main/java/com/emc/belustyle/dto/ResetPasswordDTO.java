package com.emc.belustyle.dto;

import lombok.Data;

@Data
public class ResetPasswordDTO {
    private String email;
    private String password;
    private String token;
    private String oldPassword;
    private String newPassword;
}
