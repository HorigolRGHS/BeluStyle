package com.emc.belustyle.entity;

import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JsonView(Views.ListView.class)
    private int stockId;

    @Column(name = "stock_name")
    @JsonView(Views.ListView.class)
    private String stockName;

    @Column(name = "stock_address")
    @JsonView(Views.ListView.class)
    private String stockAddress;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    @JsonView(Views.DetailedView.class)
    private List<StockProduct> stockProducts;

}
