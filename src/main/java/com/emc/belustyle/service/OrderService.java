package com.emc.belustyle.service;

import com.emc.belustyle.dto.OrderDTO;
import com.emc.belustyle.entity.Order;
import com.emc.belustyle.repo.OrderRepository;
import com.emc.belustyle.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    // Lấy tất cả các đơn hàng
    public List<OrderDTO> findAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy tất cả các đơn hàng cùng với tổng số lượng sản phẩm
    public List<OrderDTO> getAllOrdersWithQuantity() {
        List<Object[]> results = orderRepository.findAllOrdersWithQuantity();
        List<OrderDTO> orders = new ArrayList<>();

        for (Object[] result : results) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId((String) result[0]);
            orderDTO.setOrderDate((java.util.Date) result[1]);
            orderDTO.setTotalAmount((Double) result[2]);
            orderDTO.setOrderStatus((String) result[3]);
            orderDTO.setShippingMethod((String) result[4]);
            orderDTO.setPaymentMethod((String) result[5]);
            orderDTO.setTrackingNumber((String) result[6]);
            orderDTO.setNotes((String) result[7]);
            orderDTO.setDiscountCode((String) result[8]);
            orderDTO.setBillingAddress((String) result[9]);
            orderDTO.setExpectedDeliveryDate((java.util.Date) result[10]);
            orderDTO.setTransactionReference((String) result[11]);
            orderDTO.setUserAddress((String) result[12]);
            orderDTO.setTotalQuantity(((Number) result[13]).longValue());  // Tổng số lượng sản phẩm

            orders.add(orderDTO);
        }

        return orders;
    }

    // Lấy đơn hàng theo ID
    public OrderDTO findById(String id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(this::convertToDTO).orElse(null);
    }
    // lấy đơn hàng theo userId
    public List<OrderDTO> findOrdersByUserId(String userId) {
        List<Order> orders = orderRepository.findOrdersByUserId(userId);
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    // filter admin
    public List<OrderDTO> filterOrdersForAdmin(int page, int itemPerPage, String status, String payment, String userId, String staffId) {
        Pageable pageable = PageRequest.of(page - 1, itemPerPage); // Tạo Pageable với page và số item per page
        List<Order> orders = orderRepository.filterOrders(status, payment, userId, staffId, pageable);
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    // Xóa đơn hàng theo ID
    @Transactional
    public boolean deleteOrder(String id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Tạo mới đơn hàng
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        // Tạo OrderID theo định dạng cố định nếu chưa có orderId
        if (orderDTO.getOrderId() == null || orderDTO.getOrderId().isEmpty()) {
            orderDTO.setOrderId(generateFixedFormatOrderId());
        }

        Order order = convertToEntity(orderDTO);

        order.setUser(userRepository.findById(orderDTO.getUserId()).orElse(null));
        if (orderDTO.getStaffId() != null) {
            order.setStaff(userRepository.findById(orderDTO.getStaffId()).orElse(null));
        } else {
            order.setStaff(null);
        }

        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    // Phương thức tạo OrderID theo form cố định
    private String generateFixedFormatOrderId() {
        // Tìm OrderID lớn nhất hiện tại
        String lastOrderId = orderRepository.findMaxOrderId();

        // Nếu chưa có OrderID nào, bắt đầu từ B00001
        if (lastOrderId == null) {
            return "B00001";
        }

        // Lấy phần số từ OrderID và tăng lên 1
        int nextId = Integer.parseInt(lastOrderId.substring(1)) + 1;

        // Trả về OrderID mới với định dạng Bxxxxx
        return String.format("B%05d", nextId);  // %05d để đảm bảo 5 chữ số
    }



    // Cập nhật đơn hàng
    @Transactional
    public OrderDTO updateOrder(OrderDTO updatedOrderDTO) {
        Optional<Order> existingOrder = orderRepository.findById(updatedOrderDTO.getOrderId());
        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            // Cập nhật các trường của Order từ OrderDTO
            order.setOrderDate(updatedOrderDTO.getOrderDate());
            order.setTotalAmount(updatedOrderDTO.getTotalAmount());
            order.setOrderStatus(updatedOrderDTO.getOrderStatus());
            order.setShippingMethod(updatedOrderDTO.getShippingMethod());
            order.setPaymentMethod(updatedOrderDTO.getPaymentMethod());
            order.setTrackingNumber(updatedOrderDTO.getTrackingNumber());
            order.setNotes(updatedOrderDTO.getNotes());
            order.setDiscountCode(updatedOrderDTO.getDiscountCode());
            order.setBillingAddress(updatedOrderDTO.getBillingAddress());
            order.setExpectedDeliveryDate(updatedOrderDTO.getExpectedDeliveryDate());
            order.setTransactionReference(updatedOrderDTO.getTransactionReference());
            order.setUserAddress(updatedOrderDTO.getUserAddress());
            return convertToDTO(orderRepository.save(order));
        }
        return null;
    }

    // Chuyển đổi từ Order Entity sang OrderDTO
    private OrderDTO convertToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setShippingMethod(order.getShippingMethod());
        orderDTO.setPaymentMethod(order.getPaymentMethod());
        orderDTO.setTrackingNumber(order.getTrackingNumber());
        orderDTO.setNotes(order.getNotes());
        orderDTO.setDiscountCode(order.getDiscountCode());
        orderDTO.setBillingAddress(order.getBillingAddress());
        orderDTO.setExpectedDeliveryDate(order.getExpectedDeliveryDate());
        orderDTO.setTransactionReference(order.getTransactionReference());
        orderDTO.setUserAddress(order.getUserAddress());

        // Kiểm tra null cho user
        if (order.getUser() != null) {
            orderDTO.setUserId(order.getUser().getUserId());
        } else {
            orderDTO.setUserId(null);
        }

        // Kiểm tra null cho staff
        if (order.getStaff() != null) {
            orderDTO.setStaffId(order.getStaff().getUserId());
        } else {
            orderDTO.setStaffId(null);
        }
        return orderDTO;
    }

    // Chuyển đổi từ OrderDTO sang Order Entity
    private Order convertToEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderId(orderDTO.getOrderId());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setOrderStatus(orderDTO.getOrderStatus());
        order.setShippingMethod(orderDTO.getShippingMethod());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setTrackingNumber(orderDTO.getTrackingNumber());
        order.setNotes(orderDTO.getNotes());
        order.setDiscountCode(orderDTO.getDiscountCode());
        order.setBillingAddress(orderDTO.getBillingAddress());
        order.setExpectedDeliveryDate(orderDTO.getExpectedDeliveryDate());
        order.setTransactionReference(orderDTO.getTransactionReference());
        order.setUserAddress(orderDTO.getUserAddress());
        return order;
    }
}
