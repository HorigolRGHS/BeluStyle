package com.emc.belustyle.entity;

import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    @JsonView({Views.StockView.class, Views.ListView.class, Views.TransactionView.class})
    private int stockId;

    @Column(name = "stock_name")
    @JsonView({Views.StockView.class, Views.ListView.class, Views.TransactionView.class})
    private String stockName;

    @Column(name = "stock_address")
    @JsonView({Views.StockView.class, Views.ListView.class})
    private String stockAddress;

    @OneToMany(mappedBy = "stock")
    @JsonView(Views.StockView.class)
    private List<StockProduct> stockProducts;


}
