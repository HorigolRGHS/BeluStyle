package com.emc.belustyle.entity;

import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock_product")
public class StockProduct {

    @EmbeddedId
    private StockProductId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("variationId")
    @JoinColumn(name = "variation_id")
    @JsonView(Views.StockView.class)
    private ProductVariation productVariation;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("stockId")
    @JoinColumn(name = "stock_id")
    @JsonIgnore
    private Stock stock;

    @Column(name = "quantity")
    @JsonView(Views.StockView.class)
    private int quantity;

    // Embedded ID Class
    @Embeddable
    public static class StockProductId implements Serializable {
        private int variationId;
        private int stockId;
    }

}

