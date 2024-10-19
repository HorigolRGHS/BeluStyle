package com.emc.belustyle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ViewUserDTO {
    private String userId;
    private String username;
    private String email;
    private Boolean enable;
    private Date createdAt;
    private Date updatedAt;
}
