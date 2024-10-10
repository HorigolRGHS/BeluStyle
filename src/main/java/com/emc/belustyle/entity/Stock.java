package com.emc.belustyle.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private int stockId;

    @Column(name = "stock_name", nullable = false)
    private String stockName;

    @Column(name = "stock_address", nullable = false)
    private String stockAddress;

    @OneToMany(mappedBy = "stock")
    private List<StockTransaction> stockTransactions;

    @OneToMany(mappedBy = "stock")
    private Set<StockProduct> stockProducts = new HashSet<>();
}

