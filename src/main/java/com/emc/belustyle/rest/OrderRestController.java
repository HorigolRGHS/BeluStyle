package com.emc.belustyle.rest;

import com.emc.belustyle.dto.OrderDTO;
import com.emc.belustyle.entity.Order;
import com.emc.belustyle.service.OrderService;
import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {
    private final OrderService orderService;

    @Autowired
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @JsonView(Views.OrderView.class)
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable String orderId) {
        OrderDTO order = orderService.findById(orderId);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @PutMapping("/{id}")
    public OrderDTO updateOrder(@PathVariable String id, @RequestBody OrderDTO orderDTO) {
        return orderService.updateOrder(id, orderDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
    }
}
