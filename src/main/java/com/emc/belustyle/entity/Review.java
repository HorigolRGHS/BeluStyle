package com.emc.belustyle.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer reviewId;

    @Column(name = "order_detail_id")
    private Integer orderDetailId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "review_rating")
    private Integer reviewRating;

    @Column(name = "review_comment", columnDefinition = "TEXT CHARACTER SET UTF8MB4")
    private String reviewComment;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

}

