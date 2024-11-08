package com.emc.belustyle.dto.mapper;

import com.emc.belustyle.dto.StockDTO;
import com.emc.belustyle.dto.StockTransactionDTO;
import com.emc.belustyle.entity.Stock;
import com.emc.belustyle.entity.StockTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StockMapper {

    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

    StockDTO toDTO(Stock stock);

    Stock toEntity(StockDTO stockDTO);
}
