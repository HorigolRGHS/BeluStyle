package com.emc.belustyle.dto.mapper;

import com.emc.belustyle.dto.ProductDTO;
import com.emc.belustyle.entity.ProductVariation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductVariationMapper {

    @Mapping(source = "size.sizeId", target = "sizeId")
    @Mapping(source = "color.colorId", target = "colorId")
    @Mapping(source = "productPrice", target = "productPrice")
    @Mapping(source = "productVariationImage", target = "productVariationImage")
    ProductDTO.ProductVariationDTO toDTO(ProductVariation variation);
}
