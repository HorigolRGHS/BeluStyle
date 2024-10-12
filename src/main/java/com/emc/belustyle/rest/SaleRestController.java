package com.emc.belustyle.rest;

import com.emc.belustyle.dto.SaleDTO;
import com.emc.belustyle.dto.SaleProductDTO;
import com.emc.belustyle.entity.Sale;
import com.emc.belustyle.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleRestController {

    @Autowired
    private SaleService saleService;

    @PostMapping
    public ResponseEntity<Sale> createSale(@RequestBody SaleProductDTO saleProductDTO) {
        Sale sale = saleService.createSale(saleProductDTO);
        return ResponseEntity.ok(sale);
    }
}
