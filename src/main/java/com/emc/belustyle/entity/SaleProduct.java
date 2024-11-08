
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
@Table(name = "sale_product")
public class SaleProduct{

    @EmbeddedId
    private SaleProductId id;

    @MapsId("saleId")
    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Embeddable
    @Data
    public static class SaleProductId implements Serializable {
        private int saleId;
        private String productId;
    }

}


