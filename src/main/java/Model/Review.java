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
public class Review {
     private int reviewId;
    private String userId;
    private int productId;
    private int reviewRating;
    private String reviewComment;
    private Timestamp createdAt;

    public Review() {
    }

    public Review(int reviewId, String userId, int productId, int reviewRating, String reviewComment, Timestamp createdAt) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.productId = productId;
        this.reviewRating = reviewRating;
        this.reviewComment = reviewComment;
        this.createdAt = createdAt;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Review{" + "reviewId=" + reviewId + ", userId=" + userId + ", productId=" + productId + ", reviewRating=" + reviewRating + ", reviewComment=" + reviewComment + ", createdAt=" + createdAt + '}';
    }
    
    

}
