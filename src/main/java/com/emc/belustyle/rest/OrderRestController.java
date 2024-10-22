package com.emc.belustyle.rest;

import com.emc.belustyle.dto.OrderDTO;
import com.emc.belustyle.dto.ResponseDTO;
import com.emc.belustyle.entity.Order;
import com.emc.belustyle.service.OrderService;
import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;

    @Autowired
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Lấy danh sách tất cả đơn hàng
    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    // Lấy chi tiết đơn hàng theo orderId
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    @JsonView(Views.DetailedView.class)
    public ResponseEntity<?> getOrderById(@PathVariable String id) {
        ResponseDTO responseDTO = new ResponseDTO();
        OrderDTO order = orderService.findById(id);
        if (order == null) {
            responseDTO.setStatusCode(HttpStatus.NO_CONTENT.value());
            responseDTO.setMessage("No order found");
            return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(order);
        }
    }

    // Xóa đơn hàng theo orderId
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteOrderById(@PathVariable String id) {
        ResponseDTO responseDTO = new ResponseDTO();
        if (orderService.deleteOrder(id)) {
            responseDTO.setMessage("Order deleted successfully");
            responseDTO.setStatusCode(200);
            return ResponseEntity.status(200).body(responseDTO);
        } else {
            responseDTO.setStatusCode(404);
            responseDTO.setMessage("Order not found");
            return ResponseEntity.status(404).body(responseDTO);
        }
    }

    // Tạo đơn hàng mới
    @PreAuthorize("permitAll()")
    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    // Cập nhật đơn hàng
    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody OrderDTO orderDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        OrderDTO updatedOrder = orderService.updateOrder(orderDTO); // Không truyền orderId riêng
        if (updatedOrder == null) {
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());
            responseDTO.setMessage("No order found");
            return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
        }
    }
    // Lấy đơn hàng theo userId
    @PreAuthorize("permitAll()")
    @GetMapping("/by-user")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@RequestParam(required = false) String userId) {
        List<OrderDTO> orders;
        if (userId != null) {
            orders = orderService.findOrdersByUserId(userId);
        } else {
            orders = orderService.findAll();  // Nếu không có userId, trả về tất cả đơn hàng
        }
        return ResponseEntity.ok(orders);
    }
    // API dành cho admin để lọc danh sách đơn hàng
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<OrderDTO>> getFilteredOrdersForAdmin(
            @RequestParam int page,
            @RequestParam int itemPerPage,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String payment,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String staffId) {

        List<OrderDTO> orders = orderService.filterOrdersForAdmin(page, itemPerPage, status, payment, userId, staffId);
        return ResponseEntity.ok(orders);
    }


}
