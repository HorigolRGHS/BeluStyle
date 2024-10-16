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

    public List<OrderDetailDTO> getAllOrderDetails() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        return orderDetails.stream()
                .map(OrderDetailMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public OrderDetailDTO getOrderDetailById(Integer id) {
        Optional<OrderDetail> orderDetail = orderDetailRepository.findById(id);
        return orderDetail.map(OrderDetailMapper.INSTANCE::toDTO).orElse(null);
    }

    public OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = OrderDetailMapper.INSTANCE.toEntity(orderDetailDTO);
        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
        return OrderDetailMapper.INSTANCE.toDTO(savedOrderDetail);
    }

    public OrderDetailDTO updateOrderDetail(Integer id, OrderDetailDTO orderDetailDTO) {
        Optional<OrderDetail> existingOrderDetail = orderDetailRepository.findById(id);
        if (existingOrderDetail.isPresent()) {
            OrderDetail orderDetailToUpdate = existingOrderDetail.get();
            // Cập nhật các thuộc tính thủ công
            orderDetailToUpdate.setVariationId(orderDetailDTO.getVariationId());
            orderDetailToUpdate.setOrderQuantity(orderDetailDTO.getOrderQuantity());
            orderDetailToUpdate.setUnitPrice(orderDetailDTO.getUnitPrice());
            orderDetailToUpdate.setDiscountAmount(orderDetailDTO.getDiscountAmount());
            // Không cần thiết cập nhật order nếu không thay đổi
            OrderDetail updatedOrderDetail = orderDetailRepository.save(orderDetailToUpdate);
            return OrderDetailMapper.INSTANCE.toDTO(updatedOrderDetail);
        }
        return null;
    }

    public boolean deleteOrderDetail(Integer id) {
        if (orderDetailRepository.existsById(id)) {
            orderDetailRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
