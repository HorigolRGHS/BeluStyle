package com.emc.belustyle.entity;

import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(Views.ListView.class)
    private Integer discountId;

    @Column(name = "discount_code", nullable = false, length = 20)
    @JsonView(Views.ListView.class)
    private String discountCode;

    @Column(name = "discount_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonView(Views.ListView.class)
    private DiscountType discountType;

    @Column(name = "discount_value", nullable = false, precision = 10, scale = 2)
    @JsonView(Views.ListView.class)
    private BigDecimal discountValue;

    @Column(name = "start_date")
    @JsonView(Views.ListView.class)
    private Date startDate;

    @Column(name = "end_date")
    @JsonView(Views.ListView.class)
    private Date endDate;

    @Column(name = "discount_status", nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonView(Views.ListView.class)
    private DiscountStatus discountStatus;

    @Column(name = "discount_description", columnDefinition = "TEXT CHARACTER SET UTF8MB4")
    @JsonView(Views.ListView.class)
    private String discountDescription;

    @Column(name = "minimum_order_value", precision = 10, scale = 2)
    @JsonView(Views.ListView.class)
    private BigDecimal minimumOrderValue;

    @Column(name = "maximum_discount_value", precision = 10, scale = 2)
    @JsonView(Views.ListView.class)
    private BigDecimal maximumDiscountValue;

    @Column(name = "usage_limit", nullable = false)
    @JsonView(Views.ListView.class)
    private Integer usageLimit;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(Views.ListView.class)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(Views.ListView.class)
    private Date updatedAt;

    public enum DiscountType {
        PERCENTAGE, FIXED_AMOUNT
    }

    public enum DiscountStatus {
        ACTIVE, EXPIRED, USED
    }

}
