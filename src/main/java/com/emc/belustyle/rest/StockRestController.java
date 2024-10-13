package com.emc.belustyle.rest;

import com.emc.belustyle.dto.StockDTO;
import com.emc.belustyle.dto.mapper.StockMapper;
import com.emc.belustyle.entity.Stock;
import com.emc.belustyle.service.StockService;
import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockRestController {

    private StockService stockService;

    @Autowired
    public StockRestController(StockService stockService) {
        this.stockService = stockService;
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @GetMapping
    @JsonView(Views.ListView.class)
    public List<Stock> getStocks() {
         return stockService.findAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @GetMapping("/{id}")
    @JsonView(Views.DetailedView.class)
    public Stock getStockById(@PathVariable int id) {
        return stockService.findById(id).orElse(null);
    }


}
