package com.emc.belustyle.entity;

import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

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
    @JsonView({Views.StockView.class, Views.TransactionView.class})
    private int colorId;

    @Column(name = "color_name")
    @JsonView({Views.StockView.class, Views.TransactionView.class})
    private String colorName;

    @Column(name = "hex_code")
    @JsonView({Views.StockView.class, Views.TransactionView.class})
    private String hexCode;

}
