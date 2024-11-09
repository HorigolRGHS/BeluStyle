package com.emc.belustyle.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "user_discount")
public class UserDiscount {

    @EmbeddedId
    private UserDiscountId id;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @MapsId("discountId")
    @ManyToOne
    @JoinColumn(name = "discount_id", nullable = false)
    private Discount discount;

    @Column(name = "usage_count", nullable = false)
    private Integer usageCount = 0;

    @Column(name = "used_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usedAt;


    public UserDiscount(User user, Discount discount) {
        this.id = new UserDiscountId(user.getUserId(), discount.getDiscountId());
        this.user = user;
        this.discount = discount;
        this.usageCount = 0;
        this.usedAt = null;
    }

    @Data
    @Embeddable
    public static class UserDiscountId implements Serializable {
        private String userId;
        private Integer discountId;

        public UserDiscountId() {}

        public UserDiscountId(String userId, Integer discountId) {
            this.userId = userId;
            this.discountId = discountId;
        }
    }
}
