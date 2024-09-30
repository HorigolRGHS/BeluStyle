package com.emc.belustyle.entity;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "stock_transaction")
public class StockTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    @Column(name = "stock_id", nullable = false)
    private Integer stockId;

    @Column(name = "variation_id", nullable = false)
    private Integer variationId;

    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "transaction_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    public enum TransactionType {
        IN, OUT
    }

}
