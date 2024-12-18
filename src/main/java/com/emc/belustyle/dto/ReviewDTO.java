package com.emc.belustyle.dto;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Integer orderDetailId;
    private Integer reviewId;
    private String productId;
    private Integer reviewRating;
    private String reviewComment;
    private Date createdAt;
    private String username;
    private String orderId;
}
