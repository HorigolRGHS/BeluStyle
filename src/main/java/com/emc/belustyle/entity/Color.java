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
@Table(name = "color")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private int colorId;

    @Column(name = "color_name")
    private String colorName;

    @Column(name = "hex_code")
    private String hexCode;

    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL)
    private List<ProductVariation> variations;

}
