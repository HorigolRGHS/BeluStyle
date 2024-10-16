package com.emc.belustyle.service;

import com.emc.belustyle.dto.StockTransactionDTO;
import com.emc.belustyle.dto.mapper.StockTransactionMapper;
import com.emc.belustyle.entity.StockTransaction;
import com.emc.belustyle.repo.ProductRepository;
import com.emc.belustyle.repo.ProductVariationRepository;
import com.emc.belustyle.repo.StockRepository;
import com.emc.belustyle.repo.StockTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockTransactionService {

    private final ProductVariationRepository productVariationRepository;
    private StockTransactionRepository stockTransactionRepository;
    private StockRepository stockRepository;
    private ProductRepository productRepository;


    @Autowired
    public StockTransactionService(StockTransactionRepository stockTransactionRepository, StockRepository stockRepository, ProductRepository productRepository, ProductVariationRepository productVariationRepository) {
        this.stockTransactionRepository = stockTransactionRepository;
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
        this.productVariationRepository = productVariationRepository;
    }

    public StockTransaction createStockTransaction(StockTransactionDTO stockTransactionDTO) {
        StockTransaction stockTransaction = StockTransactionMapper.INSTANCE.toEntity(stockTransactionDTO);
        stockTransaction.setStock(stockRepository.findById(stockTransactionDTO.getStockId()).get());
        stockTransaction.setProductVariation(productVariationRepository.findById(stockTransactionDTO.getVariationId()).get());
        return stockTransactionRepository.save(stockTransaction);
    }

    public List<StockTransaction> findAll() {
        return stockTransactionRepository.findAll();
    }
}
