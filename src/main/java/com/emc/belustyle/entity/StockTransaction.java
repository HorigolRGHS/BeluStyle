package com.emc.belustyle.entity;

import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Data
@Table(name = "stock_transaction")
public class StockTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    @JsonView(Views.TransactionView.class)
    private Integer transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    @JsonView(Views.TransactionView.class)
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variation_id")
    @JsonView(Views.TransactionView.class)
    private ProductVariation productVariation;

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    @JsonView(Views.TransactionView.class)
    private TransactionType transactionType;

    @Column(name = "quantity")
    @JsonView(Views.TransactionView.class)
    private Integer quantity;

    @Column(name = "transaction_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(Views.TransactionView.class)
    private Date transactionDate;

    public enum TransactionType {
        IN, OUT
    }

    @PrePersist
    protected void onCreate() {
        transactionDate = new Date();
    }

}
