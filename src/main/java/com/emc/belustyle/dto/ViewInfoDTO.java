package com.emc.belustyle.dto;

import com.emc.belustyle.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ViewInfoDTO {
    private String username;
    private String email;
    private String fullName;
    private String userImage;
    private String currentPaymentMethod;
    private String userAddress;
    private Date createdAt;
    private Date updatedAt;
}
