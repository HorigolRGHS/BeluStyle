package com.emc.belustyle.entity;


import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "user_discount")
public class UserDiscount {

    @Id
    @Column(name = "user_discount_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userDiscountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    @JsonView(Views.OrderView.class)
    private Discount discount;

    @Column(name = "usage_count", nullable = false)
    @JsonView(Views.OrderView.class)
    private Integer usageCount;

    @Column(name = "used_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(Views.OrderView.class)
    private Date usedAt;

}

