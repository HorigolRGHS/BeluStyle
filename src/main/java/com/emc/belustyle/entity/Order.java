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
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    @JsonView(Views.ListView.class)
    private String orderId;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(Views.ListView.class)
    private Date orderDate;

    @Column(name = "order_status")
    @JsonView(Views.ListView.class)
    private String orderStatus;

    @Column(name = "notes")
    @JsonView(Views.ListView.class)
    private String notes;

    @Column(name = "discount_code")
    @JsonView(Views.ListView.class)
    private String discountCode;

    @Column(name = "billing_address")
    @JsonView(Views.ListView.class)
    private String billingAddress;

    @Column(name = "expected_delivery_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(Views.ListView.class)
    private Date expectedDeliveryDate;

    @Column(name = "shipping_method")
    @JsonView(Views.ListView.class)
    private String shippingMethod;

    @Column(name = "total_amount")
    @JsonView(Views.ListView.class)
    private Double totalAmount;

    @Column(name = "payment_method")
    @JsonView(Views.ListView.class)
    private String paymentMethod;

    @Column(name = "tracking_number")
    @JsonView(Views.ListView.class)
    private String trackingNumber;

    @Column(name = "transaction_reference")
    @JsonView(Views.ListView.class)
    private String transactionReference;

    @Column(name = "user_address")
    @JsonView(Views.ListView.class)
    private String userAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.DetailedView.class)
    private User user;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    @JsonView(Views.DetailedView.class)
    private User staff;
}
