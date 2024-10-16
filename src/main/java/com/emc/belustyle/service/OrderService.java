package com.emc.belustyle.service;

import com.emc.belustyle.dto.OrderDTO;
import com.emc.belustyle.dto.mapper.OrderMapper;
import com.emc.belustyle.entity.Order;
import com.emc.belustyle.repo.OrderRepository;
import com.emc.belustyle.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public List<OrderDTO> findAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO findById(String id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(OrderMapper.INSTANCE::toDTO).orElse(null);
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }

        Order order = OrderMapper.INSTANCE.toEntity(orderDTO);
        order.setUser(userRepository.findById(orderDTO.getUserId()).orElse(null));
        order.setStaff(userRepository.findById(orderDTO.getStaffId()).orElse(null));

        return OrderMapper.INSTANCE.toDTO(orderRepository.save(order));
    }

    public OrderDTO updateOrder(String id, OrderDTO orderDTO) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent() && orderDTO != null) {
            Order orderToUpdate = existingOrder.get();
            // Cập nhật các thuộc tính
            orderToUpdate.setOrderDate(orderDTO.getOrderDate());
            orderToUpdate.setOrderStatus(orderDTO.getOrderStatus());
            orderToUpdate.setNotes(orderDTO.getNotes());
            orderToUpdate.setDiscountCode(orderDTO.getDiscountCode());
            orderToUpdate.setBillingAddress(orderDTO.getBillingAddress());
            orderToUpdate.setExpectedDeliveryDate(orderDTO.getExpectedDeliveryDate());
            orderToUpdate.setShippingMethod(orderDTO.getShippingMethod());
            orderToUpdate.setTotalAmount(orderDTO.getTotalAmount());
            orderToUpdate.setPaymentMethod(orderDTO.getPaymentMethod());
            orderToUpdate.setTrackingNumber(orderDTO.getTrackingNumber());
            orderToUpdate.setTransactionReference(orderDTO.getTransactionReference());
            orderToUpdate.setUserAddress(orderDTO.getUserAddress());
            orderToUpdate.setUser(userRepository.findById(orderDTO.getUserId()).orElse(null));
            orderToUpdate.setStaff(userRepository.findById(orderDTO.getStaffId()).orElse(null));

            return OrderMapper.INSTANCE.toDTO(orderRepository.save(orderToUpdate));
        }
        return null;
    }

    public boolean deleteOrder(String id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
