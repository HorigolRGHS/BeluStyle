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
    @JsonView(Views.ListView.class)
    private String productId;

    @Column(name = "product_name")
    @JsonView(Views.ListView.class)
    private String productName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonView(Views.DetailedView.class)
    private Category category;

    @ManyToOne()
    @JoinColumn(name = "brand_id")
    @JsonView(Views.DetailedView.class)
    private Brand brand;

    @Column(name = "product_description")
    @JsonView(Views.ListView.class)
    private String productDescription;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(Views.ListView.class)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(Views.ListView.class)
    private Date updatedAt;


}

