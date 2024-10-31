package com.emc.belustyle.dto.mapper;

import com.emc.belustyle.dto.OrderDTO;
import com.emc.belustyle.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = OrderDetailMapper.class)
public interface OrderMapper {

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "staff.userId", target = "staffId")
    @Mapping(source = "orderDetails", target = "orderDetails")
    OrderDTO toDTO(Order order);

    @Mapping(source = "userId", target = "user.userId")
    @Mapping(source = "staffId", target = "staff.userId")
    @Mapping(source = "orderDetails", target = "orderDetails")
    Order toEntity(OrderDTO orderDTO);
}
