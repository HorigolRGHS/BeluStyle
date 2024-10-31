package com.emc.belustyle.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDetailDTO {
    private Integer orderDetailId;
    private String orderId;
    private Integer variationId; // ID của biến thể sản phẩm
    private Integer orderQuantity; // Số lượng đặt
    private BigDecimal unitPrice; // Đơn giá
    private BigDecimal discountAmount; // Số tiền giảm giá
    private StockTransactionDTO stockTransactionDTO; // Thông tin giao dịch kho
    private int quantity; // Số lượng còn lại trong kho
    private Integer stockId; // ID kho
}
