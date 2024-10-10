package com.emc.belustyle.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "size")
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_id")
    private int sizeId;

    @Column(name = "size_name")
    private String sizeName;

    @OneToMany(mappedBy = "size")
    private List<ProductVariation> productVariations;
}

