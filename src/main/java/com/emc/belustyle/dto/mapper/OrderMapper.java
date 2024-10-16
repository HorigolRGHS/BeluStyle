package com.emc.belustyle.dto.mapper;

import com.emc.belustyle.dto.OrderDTO;
import com.emc.belustyle.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "staff.userId", target = "staffId")
    OrderDTO toDTO(Order order);

    Order toEntity(OrderDTO orderDTO);
}
