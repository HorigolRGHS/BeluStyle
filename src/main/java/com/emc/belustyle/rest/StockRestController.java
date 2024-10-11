package com.emc.belustyle.rest;

import com.emc.belustyle.entity.Stock;
import com.emc.belustyle.repo.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks/")
public class StockRestController {

    private StockRepository stockRepository;

    @Autowired
    public StockRestController(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @GetMapping
    public Iterable<Stock> getStocks() {
        return stockRepository.findAll();
    }
}
