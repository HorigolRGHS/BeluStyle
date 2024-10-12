package com.emc.belustyle.entity;


import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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

    @Column(name = "user_id")
    @JsonView(Views.ListView.class)
    private String userId;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(Views.ListView.class)
    private Date orderDate;

    @Column(name = "total_amount", precision = 10, scale = 2)
    @JsonView(Views.ListView.class)
    private BigDecimal totalAmount;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    @JsonView(Views.ListView.class)
    private OrderStatus orderStatus;

    @Column(name = "user_address", columnDefinition = "VARCHAR(255) CHARACTER SET UTF8MB4")
    @JsonView(Views.ListView.class)
    private String userAddress;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    @JsonView(Views.ListView.class)
    private PaymentMethod paymentMethod;

    @Column(name = "shipping_method", length = 50)
    @JsonView(Views.ListView.class)
    private String shippingMethod;

    @Column(name = "tracking_number", length = 50)
    @JsonView(Views.ListView.class)
    private String trackingNumber;

    @Column(name = "notes", columnDefinition = "TEXT CHARACTER SET UTF8MB4")
    @JsonView(Views.ListView.class)
    private String notes;

    @Column(name = "discount_code", length = 50)
    @JsonView(Views.ListView.class)
    private String discountCode;

    @Column(name = "billing_address", columnDefinition = "VARCHAR(255) CHARACTER SET UTF8MB4")
    @JsonView(Views.ListView.class)
    private String billingAddress;

    @Column(name = "expected_delivery_date")
    @Temporal(TemporalType.DATE)
    @JsonView(Views.ListView.class)
    private Date expectedDeliveryDate;

    @Column(name = "transaction_reference", length = 255)
    private String transactionReference;

    @Column(name = "staff_id")
    private String staffId;

    public enum OrderStatus {
        PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    }

    public enum PaymentMethod {
        COD, VNPAY, PAYOS, TRANSFER
    }
}
