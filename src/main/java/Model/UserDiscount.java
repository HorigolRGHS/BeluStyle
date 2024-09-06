/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Le Khac Huy - CE180311
 */
public class UserDiscount {
    private int userDiscountId;
    private String userId;
    private int discountId;
    private boolean isUsed;

    public UserDiscount() {
    }

    public UserDiscount(int userDiscountId, String userId, int discountId, boolean isUsed) {
        this.userDiscountId = userDiscountId;
        this.userId = userId;
        this.discountId = discountId;
        this.isUsed = isUsed;
    }

    public int getUserDiscountId() {
        return userDiscountId;
    }

    public void setUserDiscountId(int userDiscountId) {
        this.userDiscountId = userDiscountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public boolean isIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    @Override
    public String toString() {
        return "UserDiscount{" + "userDiscountId=" + userDiscountId + ", userId=" + userId + ", discountId=" + discountId + ", isUsed=" + isUsed + '}';
    }
    
     
}
