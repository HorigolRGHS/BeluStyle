package com.emc.belustyle.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private int variationId;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @Column(name = "product_variation_image")
    private String productVariationImage;

    @Column(name = "product_price", precision = 10, scale = 2)
    private BigDecimal productPrice;

    @OneToMany(mappedBy = "productVariation")
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "productVariation")
    private List<StockTransaction> stockTransactions;

    @OneToMany(mappedBy = "productVariation")
    private Set<StockProduct> stockProducts = new HashSet<>();
}

