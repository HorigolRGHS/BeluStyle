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
@Table(name = "order") // Đổi tên bảng thành orders
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    @JsonView(Views.OrderView.class)
    private String orderId;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(Views.OrderView.class)
    private Date orderDate;

    @Column(name = "order_status")
    @JsonView(Views.OrderView.class)
    private String orderStatus;

    @Column(name = "notes")
    @JsonView(Views.OrderView.class)
    private String notes;

    @Column(name = "discount_code")
    @JsonView(Views.OrderView.class)
    private String discountCode;

    @Column(name = "billing_address")
    @JsonView(Views.OrderView.class)
    private String billingAddress;

    @Column(name = "expected_delivery_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(Views.OrderView.class)
    private Date expectedDeliveryDate;

    @Column(name = "shipping_method")
    @JsonView(Views.OrderView.class)
    private String shippingMethod;

    @Column(name = "total_amount")
    @JsonView(Views.OrderView.class)
    private Double totalAmount;

    @Column(name = "payment_method")
    @JsonView(Views.OrderView.class)
    private String paymentMethod;

    @Column(name = "tracking_number")
    @JsonView(Views.OrderView.class)
    private String trackingNumber;

    @Column(name = "transaction_reference")
    @JsonView(Views.OrderView.class)
    private String transactionReference;

    @Column(name = "user_address")
    @JsonView(Views.OrderView.class)
    private String userAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.OrderView.class)
    private User user;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    @JsonView(Views.OrderView.class)
    private User staff;
}
