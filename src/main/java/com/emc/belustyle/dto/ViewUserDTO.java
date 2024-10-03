package com.emc.belustyle.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ViewUserDTO {
    private String username;
    private String email;
    private Boolean enable;
    private Date createdAt;
    private Date updatedAt;
}
