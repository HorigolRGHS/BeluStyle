package com.emc.belustyle.entity;

import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "product")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "productId")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    @JsonView({Views.StockView.class, Views.TransactionView.class, Views.ProductView.class})
    private String productId;

    @Column(name = "product_name")
    @JsonView({Views.StockView.class, Views.TransactionView.class, Views.ProductView.class})
    private String productName;

    @OneToMany(mappedBy = "product")
    @JsonView({Views.TransactionView.class, Views.ProductView.class})
    private List<ProductVariation> productVariations;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonView({Views.StockView.class, Views.TransactionView.class, Views.ProductView.class})
    private Category category;

    @ManyToOne()
    @JoinColumn(name = "brand_id")
    @JsonView({Views.StockView.class, Views.TransactionView.class, Views.ProductView.class})
    private Brand brand;

    @Column(name = "product_description")
    @JsonView({Views.StockView.class, Views.ProductView.class, Views.ProductView.class})
    private String productDescription;

    @Column(name = "created_at", updatable = false)
    @JsonView({Views.ProductView.class})
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @JsonView({Views.ProductView.class})
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

}

