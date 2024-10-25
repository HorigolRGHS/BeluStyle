package com.emc.belustyle.entity;

import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "product_variation")
public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variation_id")
    @JsonView({Views.StockView.class, Views.TransactionView.class, Views.ProductView.class})
    private int variationId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonView({Views.StockView.class, Views.TransactionView.class})
    private Product product;

    @ManyToOne
    @JoinColumn(name = "size_id")
    @JsonView({Views.StockView.class, Views.TransactionView.class, Views.ProductView.class})
    private Size size;

    @ManyToOne
    @JoinColumn(name = "color_id")
    @JsonView({Views.StockView.class, Views.TransactionView.class, Views.ProductView.class})
    private Color color;

    @Column(name = "product_variation_image")
    @JsonView({Views.StockView.class, Views.TransactionView.class, Views.ProductView.class})
    private String productVariationImage;

    @Column(name = "product_price", precision = 10, scale = 2)
    @JsonView({Views.StockView.class, Views.TransactionView.class, Views.ProductView.class})
    private BigDecimal productPrice;

//
//    @OneToMany(mappedBy = "productVariation")
//    @JsonIgnore
//    private List<StockProduct> stockProducts;
}

