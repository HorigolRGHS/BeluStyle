package com.emc.belustyle.dto.mapper;

import com.emc.belustyle.dto.StockTransactionDTO;
import com.emc.belustyle.entity.StockTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StockTransactionMapper {

    StockTransactionMapper INSTANCE = Mappers.getMapper(StockTransactionMapper.class);

    @Mapping(source = "transactionType", target = "transactionType")
    StockTransactionDTO toDTO(StockTransaction stockTransaction);

    @Mapping(source = "transactionType", target = "transactionType")
    StockTransaction toEntity(StockTransactionDTO stockTransactionDTO);
}