package com.emc.belustyle.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_detail_id", referencedColumnName = "order_detail_id", updatable = false)
    private OrderDetail orderDetail;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "review_rating")
    private Integer reviewRating;

    @Column(name = "review_comment", columnDefinition = "TEXT CHARACTER SET UTF8MB4")
    private String reviewComment;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

}

