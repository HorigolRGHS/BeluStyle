package com.emc.belustyle.dto.mapper;

import com.emc.belustyle.dto.OrderDetailDTO;
import com.emc.belustyle.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

    @Mapping(source = "order.orderId", target = "orderId")
    @Mapping(source = "variationId", target = "variationId")
    @Mapping(source = "orderQuantity", target = "orderQuantity")
    @Mapping(source = "unitPrice", target = "unitPrice")
    @Mapping(source = "discountAmount", target = "discountAmount")
    OrderDetailDTO toDTO(OrderDetail orderDetail);

    @Mapping(target = "order.orderId", source = "orderId") // Thiết lập order từ orderId
    @Mapping(source = "variationId", target = "variationId")
    @Mapping(source = "orderQuantity", target = "orderQuantity")
    @Mapping(source = "unitPrice", target = "unitPrice")
    @Mapping(source = "discountAmount", target = "discountAmount")
    OrderDetail toEntity(OrderDetailDTO orderDetailDTO);
}
