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
@ToString
@Table(name = "stock_product")
public class StockProduct {

    @EmbeddedId
    private StockProductId id;

    @ManyToOne
    @MapsId("variationId")
    @JoinColumn(name = "variation_id")
    @JsonView(Views.DetailedView.class)
    private ProductVariation productVariation;

    @ManyToOne
    @MapsId("stockId")
    @JoinColumn(name = "stock_id")
    @JsonIgnore
    private Stock stock;

    @Column(name = "quantity")
    @JsonView(Views.DetailedView.class)
    private int quantity;

    // Embedded ID Class
    @Embeddable
    public static class StockProductId implements Serializable {
        private int variationId;
        private int stockId;
    }

}

