package com.emc.belustyle.dto.mapper;

import com.emc.belustyle.dto.OrderDetailDTO;
import com.emc.belustyle.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);

    // Ánh xạ từ OrderDetail sang OrderDetailDTO
    @Mapping(source = "order.orderId", target = "orderId") // Nếu OrderDetail có thuộc tính order
    @Mapping(source = "variationId", target = "variationId") // Chỉ ánh xạ variationId
    @Mapping(source = "orderQuantity", target = "orderQuantity") // Ánh xạ orderQuantity
    @Mapping(source = "unitPrice", target = "unitPrice") // Ánh xạ unitPrice
    @Mapping(source = "discountAmount", target = "discountAmount") // Ánh xạ discountAmount
    OrderDetailDTO toDTO(OrderDetail orderDetail);

    // Ánh xạ từ OrderDetailDTO sang OrderDetail
    @Mapping(source = "orderId", target = "order.orderId") // Ánh xạ từ orderId về order
    @Mapping(source = "variationId", target = "variationId") // Ánh xạ từ variationId
    @Mapping(source = "orderQuantity", target = "orderQuantity") // Ánh xạ orderQuantity
    @Mapping(source = "unitPrice", target = "unitPrice") // Ánh xạ unitPrice
    @Mapping(source = "discountAmount", target = "discountAmount") // Ánh xạ discountAmount
    OrderDetail toEntity(OrderDetailDTO orderDetailDTO);
}
