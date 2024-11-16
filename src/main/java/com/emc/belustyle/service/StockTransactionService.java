package com.emc.belustyle.service;

import com.emc.belustyle.dto.StockTransactionDTO;
import com.emc.belustyle.dto.mapper.StockTransactionMapper;
import com.emc.belustyle.entity.ProductVariation;
import com.emc.belustyle.entity.Stock;
import com.emc.belustyle.entity.StockTransaction;
import com.emc.belustyle.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockTransactionService {

    private final ProductVariationRepository productVariationRepository;
    private StockTransactionRepository stockTransactionRepository;
    private StockRepository stockRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;


    @Autowired
    public StockTransactionService(StockTransactionRepository stockTransactionRepository, StockRepository stockRepository, ProductRepository productRepository, ProductVariationRepository productVariationRepository, UserRepository userRepository) {
        this.stockTransactionRepository = stockTransactionRepository;
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
        this.productVariationRepository = productVariationRepository;
        this.userRepository = userRepository;
    }

    public StockTransaction createStockTransaction(StockTransactionDTO stockTransactionDTO) {
        StockTransaction stockTransaction = StockTransactionMapper.INSTANCE.toEntity(stockTransactionDTO);
        stockTransaction.setStock(stockRepository.findById(stockTransactionDTO.getStockId()).get());
        stockTransaction.setProductVariation(productVariationRepository.findById(stockTransactionDTO.getVariationId()).get());
        return stockTransactionRepository.save(stockTransaction);
    }

    public List<StockTransaction> findAll() {
        return stockTransactionRepository.findAllByOrderByTransactionIdDesc();
    }

    @Transactional
    public boolean createStockTransactions(StockTransactionDTO stockTransactionDTO) {
        try {
            Optional<Stock> stock = stockRepository.findById(stockTransactionDTO.getStockId());
            if (stock.isEmpty()) {
                throw new IllegalArgumentException("Stock not found with ID: " + stockTransactionDTO.getStockId());
            }

            for (StockTransactionDTO.ProductVariationQuantity variationQuantity : stockTransactionDTO.getVariations()) {
                Optional<ProductVariation> productVariation = productVariationRepository.findById(variationQuantity.getProductVariationId());
                if (productVariation.isEmpty()) {
                    throw new IllegalArgumentException("Product variation not found with ID: " + variationQuantity.getProductVariationId());
                }

                StockTransaction stockTransaction = new StockTransaction();
                stockTransaction.setStock(stock.get());
                stockTransaction.setProductVariation(productVariation.get());
                stockTransaction.setUser(userRepository.findByUsername(stockTransactionDTO.getUsername()).orElse(null));
                stockTransaction.setTransactionType(StockTransaction.TransactionType.IN); // Assuming IN transaction
                stockTransaction.setQuantity(variationQuantity.getQuantity());

                stockTransactionRepository.save(stockTransaction);
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
