package com.emc.belustyle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter
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
    private String userId; // Thêm thông tin userId
    private String staffId; // Thêm thông tin staffId

    @JsonProperty("orderDetails") // Đảm bảo tên này giống như trong JSON
    private List<OrderDetailDTO> orderDetails;
}
