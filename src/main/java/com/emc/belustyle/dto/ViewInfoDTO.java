package com.emc.belustyle.dto;

import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewInfoDTO {
    private String userId;
    private String username;
    private String email;
    private String fullName;
    private String userImage;
    private Boolean enable;
    private String role;
    private String currentPaymentMethod;
    private String userAddress;
    private Date createdAt;
    private Date updatedAt;
}
