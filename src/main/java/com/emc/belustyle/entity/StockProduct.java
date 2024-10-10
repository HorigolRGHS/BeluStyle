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

    @EmbeddedId
    private StockProductId id;

    @ManyToOne
    @MapsId("variationId")
    @JoinColumn(name = "variation_id")
    private ProductVariation variation;

    @ManyToOne
    @MapsId("stockId")
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(name = "quantity")
    private int quantity;

    // Embedded ID Class
    @Embeddable
    public static class StockProductId implements Serializable {
        private int variationId;
        private int stockId;
    }

}

