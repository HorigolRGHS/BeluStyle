package com.emc.belustyle.dto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class OrderDTO {
    private String orderId;
    private Date orderDate;
    private String orderStatus;
    private String notes;
    private String discountCode;
    private String billingAddress;
    private Date expectedDeliveryDate;
    private String shippingMethod;
    private Double totalAmount;
    private String paymentMethod;
    private String trackingNumber;
    private String transactionReference;
    private String userAddress;
    private String userId; // ID của người dùng
    private String staffId; // ID của nhân viên
}
