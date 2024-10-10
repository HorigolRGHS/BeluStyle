package com.emc.belustyle.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    private String orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Người dùng đã đặt hàng

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "user_address", columnDefinition = "VARCHAR(255) CHARACTER SET UTF8MB4")
    private String userAddress;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "shipping_method", length = 50)
    private String shippingMethod;

    @Column(name = "tracking_number", length = 50)
    private String trackingNumber;

    @Column(name = "notes", columnDefinition = "TEXT CHARACTER SET UTF8MB4")
    private String notes;

    @Column(name = "discount_code", length = 50)
    private String discountCode;

    @Column(name = "billing_address", columnDefinition = "VARCHAR(255) CHARACTER SET UTF8MB4")
    private String billingAddress;

    @Column(name = "expected_delivery_date")
    @Temporal(TemporalType.DATE)
    private Date expectedDeliveryDate;

    @Column(name = "transaction_reference", length = 255)
    private String transactionReference;


    public enum OrderStatus {
        PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    }

    public enum PaymentMethod {
        COD, VNPAY, PAYOS, TRANSFER
    }

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private User staff; // Nhân viên xác nhận đơn hàng

    @OneToMany(mappedBy = "order")
    List<OrderDetail> orderDetails;

}
