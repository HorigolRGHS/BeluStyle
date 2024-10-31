package com.emc.belustyle.service;

import com.emc.belustyle.dto.OrderDetailDTO;
import com.emc.belustyle.dto.mapper.OrderDetailMapper;
import com.emc.belustyle.entity.OrderDetail;
import com.emc.belustyle.repo.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    public List<OrderDetailDTO> getOrderDetailsByOrderId(String orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_OrderId(orderId);
        return orderDetails.stream()
                .map(orderDetailMapper::toDTO)
                .collect(Collectors.toList());
    }
    public void saveOrderDetail(OrderDetail orderDetail) {
        if (orderDetail != null) {
            orderDetailRepository.save(orderDetail);
        } else {
            throw new IllegalArgumentException("Order detail cannot be null");
        }
    }

    public void saveAll(List<OrderDetail> orderDetails) {
        if (orderDetails != null && !orderDetails.isEmpty()) {
            orderDetailRepository.saveAll(orderDetails);
        } else {
            throw new IllegalArgumentException("Order details list cannot be null or empty");
        }
    }
}
