package com.emc.belustyle.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "user")
public class User implements UserDetails {
    @Override
    public boolean isEnabled() {
        return enable;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getRoleName().toString()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "google_id")
    private String googleId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "full_name", length = 50)
    private String fullName;

    @Column(name = "user_image")
    private String userImage;

    @Column(name = "enable")
    private Boolean enable;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole role;

    @Column(name = "current_payment_method")
    private String currentPaymentMethod;

    @Column(name = "user_address")
    private String userAddress;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

//    public User(Date updatedAt, Date createdAt, String userAddress, String currentPaymentMethod, UserRole role, Boolean enable, String userImage, String fullName, String passwordHash, String email, String googleId, String username) {
//        this.updatedAt = updatedAt;
//        this.createdAt = createdAt;
//        this.userAddress = userAddress;
//        this.currentPaymentMethod = currentPaymentMethod;
//        this.role = role;
//        this.enable = enable;
//        this.userImage = userImage;
//        this.fullName = fullName;
//        this.passwordHash = passwordHash;
//        this.email = email;
//        this.googleId = googleId;
//        this.username = username;
//    }

}

