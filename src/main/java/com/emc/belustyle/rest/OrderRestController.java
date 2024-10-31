package com.emc.belustyle.rest;

import com.emc.belustyle.dto.OrderDTO;
import com.emc.belustyle.dto.OrderDetailDTO;
import com.emc.belustyle.dto.ResponseDTO;
import com.emc.belustyle.service.OrderService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("permitAll()")
    @GetMapping
    public Page<OrderDTO> getOrders(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderService.getOrders(status, userId, pageable);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id).orElse(null);
    }

    @GetMapping("/{orderId}/details")
    public List<OrderDetailDTO> getOrderDetails(@PathVariable String orderId) {
        return orderService.getOrderDetails(orderId);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/user/{userId}")
    public Page<OrderDTO> getOrdersByUserId(
            @PathVariable String userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderService.getOrdersByUserId(userId, pageable);
    }



    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<ResponseDTO> createOrder(@RequestBody OrderDTO orderDTO, HttpServletRequest request) {
        // In ra dữ liệu nhận được
        System.out.println("Received OrderDTO: " + orderDTO);

        // Kiểm tra chi tiết từng trường trong OrderDTO
        System.out.println("Notes: " + orderDTO.getNotes());
        System.out.println("Discount Code: " + orderDTO.getDiscountCode());
        System.out.println("Billing Address: " + orderDTO.getBillingAddress());
        System.out.println("Shipping Method: " + orderDTO.getShippingMethod());
        System.out.println("Total Amount: " + orderDTO.getTotalAmount());
        System.out.println("Payment Method: " + orderDTO.getPaymentMethod());
        System.out.println("Tracking Number: " + orderDTO.getTrackingNumber());
        System.out.println("Transaction Reference: " + orderDTO.getTransactionReference());
        System.out.println("User Address: " + orderDTO.getUserAddress());
        System.out.println("User ID: " + orderDTO.getUserId());

        // In ra thông tin của các order details
        if (orderDTO.getOrderDetails() != null) {
            for (OrderDetailDTO orderDetail : orderDTO.getOrderDetails()) {
                System.out.println("Variation ID: " + orderDetail.getVariationId());
                System.out.println("Quantity: " + orderDetail.getOrderQuantity());
                System.out.println("Unit Price: " + orderDetail.getUnitPrice());
                System.out.println("Discount Amount: " + orderDetail.getDiscountAmount());
            }
        } else {
            System.out.println("No order details provided.");
        }

        try {
            // Gọi service để tạo đơn hàng
            JSONObject jsonResponse = orderService.createOrder(orderDTO, request);

            // Chuyển đổi JSONObject thành ResponseDTO
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setStatusCode(HttpStatus.OK.value());
            responseDTO.setMessage("Order created successfully.");
//            responseDTO.setToken(jsonResponse.optString("token")); // nếu bạn có token
            responseDTO.setUser(null); // nếu bạn có thông tin user

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            // Xử lý ngoại lệ và trả về lỗi
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage("Error: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/{orderId}/payment-callback")
    public ResponseEntity<ResponseDTO> handlePaymentCallback(
            @PathVariable String orderId,
            @RequestParam("isSuccess") boolean isSuccess,
            @RequestParam(value = "paymentMethod", required = false) String paymentMethod) {

        // Gọi service để xử lý callback
        orderService.handlePaymentCallback(orderId, isSuccess);

        // Tạo response
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatusCode(HttpStatus.OK.value());
        responseDTO.setMessage("Payment callback processed successfully.");

        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("permitAll()")
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<ResponseDTO> cancelOrder(@PathVariable String orderId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            orderService.cancelOrder(orderId);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            responseDTO.setMessage("Order cancelled successfully.");
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

}
