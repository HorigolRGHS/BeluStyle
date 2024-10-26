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
@Table(name = "user_discount")
public class UserDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userDiscountId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "discount_id", nullable = false)
    private Integer discountId;

    @Column(name = "usage_count", nullable = false)
    private Integer usageCount;

    @Column(name = "used_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usedAt;

    public UserDiscount(Integer discountId, String userId) {
        this.discountId = discountId;
        this.userId = userId;
        this.usageCount = 0; // Khởi tạo với giá trị mặc định là 0
        this.usedAt = null;
    }
}
