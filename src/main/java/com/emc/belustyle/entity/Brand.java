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
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    @JsonView({Views.StockView.class, Views.TransactionView.class})
    private int brandId;

    @Column(name = "brand_name", nullable = false)
    @JsonView({Views.StockView.class, Views.TransactionView.class, Views.ProductView.class})
    private String brandName;

    @Column(name = "brand_description")
    private String brandDescription;

    @Column(name = "logo_url")
    @JsonView({Views.StockView.class})
    private String logoUrl;

    @Column(name = "website_url")
    @JsonView({Views.StockView.class})
    private String websiteUrl;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
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
