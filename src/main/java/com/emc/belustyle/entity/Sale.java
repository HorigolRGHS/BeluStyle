package com.emc.belustyle.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer saleId;

    @Column(name = "sale_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SaleType saleType;

    @Column(name = "sale_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal saleValue;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "sale_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public enum SaleType {
        PERCENTAGE, FIXED_AMOUNT
    }

    public enum SaleStatus {
        ACTIVE, INACTIVE, EXPIRE
    }

    @ManyToMany(mappedBy = "sales")
    private Set<Product> products = new HashSet<>();
}
