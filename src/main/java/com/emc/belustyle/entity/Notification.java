package com.emc.belustyle.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.management.relation.Role;
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
    private Integer notificationId;

    @Column(name = "title", nullable = false, columnDefinition = "VARCHAR(255) CHARACTER SET UTF8MB4")
    private String title;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT CHARACTER SET UTF8MB4")
    private String message;


    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "target_role_id", referencedColumnName = "role_id")
    private UserRole role;

}

