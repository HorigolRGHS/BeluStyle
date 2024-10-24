package com.emc.belustyle.entity;

import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    @JsonView({Views.StockView.class, Views.TransactionView.class})
    private String productId;

    @Column(name = "product_name")
    @JsonView({Views.StockView.class, Views.TransactionView.class})
    private String productName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonView({Views.StockView.class, Views.TransactionView.class})
    private Category category;

    @ManyToOne()
    @JoinColumn(name = "brand_id")
    @JsonView({Views.StockView.class, Views.TransactionView.class})
    private Brand brand;

    @Column(name = "product_description")
    @JsonView(Views.StockView.class)
    private String productDescription;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;


}

