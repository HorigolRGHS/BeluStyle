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
@Table(name = "size")
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_id")
    @JsonView({Views.StockView.class, Views.TransactionView.class})
    private int sizeId;

    @Column(name = "size_name")
    @JsonView({Views.StockView.class, Views.TransactionView.class, Views.ProductView.class})
    private String sizeName;

}
