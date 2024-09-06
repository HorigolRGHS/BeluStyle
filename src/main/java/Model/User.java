/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Timestamp;

/**
 *
 * @author Le Khac Huy - CE180311
 */
public class User {
     private String userId;
    private String username;
    private String googleId;
    private String email;
    private String passwordHash;
    private String fullName;
    private String userImage;
    private Boolean enable;
    private String currentPaymentMethod;
    private Timestamp createAt;
    private Timestamp updateAt;

    public User() {
    }

    public User(String userId, String username, String googleId, String email, String passwordHash, String fullName, String userImage, Boolean enable, String currentPaymentMethod, Timestamp createAt, Timestamp updateAt) {
        this.userId = userId;
        this.username = username;
        this.googleId = googleId;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.userImage = userImage;
        this.enable = enable;
        this.currentPaymentMethod = currentPaymentMethod;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getCurrentPaymentMethod() {
        return currentPaymentMethod;
    }

    public void setCurrentPaymentMethod(String currentPaymentMethod) {
        this.currentPaymentMethod = currentPaymentMethod;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", username=" + username + ", googleId=" + googleId + ", email=" + email + ", passwordHash=" + passwordHash + ", fullName=" + fullName + ", userImage=" + userImage + ", enable=" + enable + ", currentPaymentMethod=" + currentPaymentMethod + ", createAt=" + createAt + ", updateAt=" + updateAt + '}';
    }
    
    
}
