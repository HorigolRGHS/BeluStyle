package com.emc.belustyle.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "stock_product")
public class StockProduct {

    @Id
    @ManyToOne
    @JoinColumn(name = "variation_id", nullable = false)
    private ProductVariation productVariation;

    @Id
    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(name = "quantity")
    private Integer quantity;

}

