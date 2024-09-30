package com.emc.belustyle.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "discount")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer discountId;

    @Column(name = "discount_code", nullable = false, length = 20)
    private String discountCode;

    @Column(name = "discount_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @Column(name = "discount_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "discount_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountStatus discountStatus;

    @Column(name = "discount_description", columnDefinition = "TEXT CHARACTER SET UTF8MB4")
    private String discountDescription;

    @Column(name = "minimum_order_value", precision = 10, scale = 2)
    private BigDecimal minimumOrderValue;

    @Column(name = "maximum_discount_value", precision = 10, scale = 2)
    private BigDecimal maximumDiscountValue;

    @Column(name = "usage_limit", nullable = false)
    private Integer usageLimit;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public enum DiscountType {
        PERCENTAGE, FIXED_AMOUNT
    }

    public enum DiscountStatus {
        ACTIVE, EXPIRED, USED
    }

}
