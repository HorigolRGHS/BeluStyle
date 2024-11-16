package com.emc.belustyle.rest;

import com.emc.belustyle.dto.StockTransactionDTO;
import com.emc.belustyle.entity.StockTransaction;
import com.emc.belustyle.service.StockTransactionService;
import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.View;
import java.util.List;

@RestController
@RequestMapping("/api/stock-transactions")
public class StockTransactionRestController {
    private StockTransactionService stockTransactionService;

    @Autowired
    public StockTransactionRestController(StockTransactionService stockTransactionService) {
        this.stockTransactionService = stockTransactionService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @GetMapping
    @JsonView(Views.TransactionView.class)
    public List<StockTransaction> getStockTransactions() {
        return stockTransactionService.findAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @PostMapping
    public ResponseEntity<String> bulkImportTransactions(
            @RequestBody StockTransactionDTO stockTransactionDTO) {
        boolean check = stockTransactionService.createStockTransactions(stockTransactionDTO);
        if (check) {
            return ResponseEntity.ok("Bulk import of stock transactions successful");

        }
        return ResponseEntity.badRequest().body("Bulk import of stock transactions failed");
    }
}
