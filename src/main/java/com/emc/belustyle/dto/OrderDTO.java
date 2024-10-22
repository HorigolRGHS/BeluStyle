package com.emc.belustyle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String orderId;              // ID của đơn hàng
    private Date orderDate;              // Ngày đặt hàng
    private String orderStatus;          // Trạng thái đơn hàng
    private String notes;                // Ghi chú từ khách hàng
    private String discountCode;         // Mã giảm giá áp dụng (nếu có)
    private String billingAddress;       // Địa chỉ thanh toán
    private Date expectedDeliveryDate;   // Ngày giao hàng dự kiến
    private String shippingMethod;       // Phương thức giao hàng
    private Double totalAmount;          // Tổng số tiền
    private String paymentMethod;        // Phương thức thanh toán (COD, VNPAY,...)
    private String trackingNumber;       // Mã vận chuyển
    private String transactionReference; // Mã tham chiếu thanh toán (nếu thanh toán qua cổng điện tử)
    private String userAddress;          // Địa chỉ của khách hàng

    private String userId;               // ID của người dùng (customer)
    private String staffId;              // ID của nhân viên xử lý đơn hàng

    private Long totalQuantity;          // Tổng số lượng sản phẩm trong đơn hàng
}
