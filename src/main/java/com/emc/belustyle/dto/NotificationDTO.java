package com.emc.belustyle.dto;

import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private int notificationId;
    @JsonView(Views.ListView.class)
    private String title;
    @JsonView(Views.ListView.class)
    private String message;
    private String targetRoleId;
    private Date createdAt;
}
