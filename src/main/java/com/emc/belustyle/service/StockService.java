package com.emc.belustyle.service;

import com.emc.belustyle.dto.StockDetailDTO;
import com.emc.belustyle.entity.Stock;
import com.emc.belustyle.repo.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    private StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    public Optional<Stock> findById(int id) {
        return stockRepository.findById(id);
    }


    public List<StockDetailDTO> getStockDetails(Integer stockId) {
        List<Object[]> stockData = stockRepository.findStockDetailsById(stockId);
        List<StockDetailDTO> stockDetailsList = new ArrayList<>();

        for (Object[] row : stockData) {
            StockDetailDTO stockDetails = new StockDetailDTO();

            stockDetails.setStockId((Integer) row[0]);
            stockDetails.setStockName((String) row[1]);
            stockDetails.setStockAddress((String) row[2]);
            stockDetails.setVariationId((Integer) row[3]);
            stockDetails.setProductPrice((BigDecimal) row[4]);
            stockDetails.setProductVariationImage((String) row[5]);
            stockDetails.setProductName((String) row[6]);
            stockDetails.setProductId((String) row[7]);
            stockDetails.setBrandId((Integer) row[8]);
            stockDetails.setBrandName((String) row[9]);
            stockDetails.setCategoryId((Integer) row[10]);
            stockDetails.setCategoryName((String) row[11]);
            stockDetails.setSizeName((String) row[12]);
            stockDetails.setColorName((String) row[13]);
            stockDetails.setHexCode((String) row[14]);
            stockDetails.setQuantity((Integer) row[15]);

            stockDetailsList.add(stockDetails);
        }

        if(stockDetailsList.size() == 1 && stockDetailsList.get(0).getProductId() == null) {
            return null;
        }

        return stockDetailsList;
    }


}
