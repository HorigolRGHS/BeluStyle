package com.emc.belustyle.dto.mapper;

import com.emc.belustyle.dto.ProductDTO;
import com.emc.belustyle.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import javax.xml.transform.Source;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    @Mapping(source = "category.categoryId", target = "categoryId")
    @Mapping(source = "brand.brandId", target = "brandId")
    ProductDTO toDTO(Product product);


    Product toEntity(ProductDTO productDTO);
}
