package com.emc.belustyle.dto;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountUserViewDTO {
    private String userId;
    private String username;
    private String email;
    private String fullName;
    private String userImage;
}
