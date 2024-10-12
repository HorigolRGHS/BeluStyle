package com.emc.belustyle.entity;

import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.ListView.class)
    private Integer notificationId;

    @Column(name = "title", nullable = false, columnDefinition = "VARCHAR(255) CHARACTER SET UTF8MB4")
    @JsonView(Views.ListView.class)
    private String title;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT CHARACTER SET UTF8MB4")
    @JsonView(Views.ListView.class)
    private String message;

    @Column(name = "target_role_id")
    @JsonView(Views.ListView.class)
    private Integer targetRoleId;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(Views.ListView.class)
    private Date createdAt;

}

