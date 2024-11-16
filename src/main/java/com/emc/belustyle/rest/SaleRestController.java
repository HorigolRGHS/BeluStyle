package com.emc.belustyle.rest;

import com.emc.belustyle.dto.ResponseDTO;
import com.emc.belustyle.dto.SaleDTO;
import com.emc.belustyle.entity.Product;
import com.emc.belustyle.entity.Sale;
import com.emc.belustyle.exception.CustomException;
import com.emc.belustyle.repo.SaleRepository;
import com.emc.belustyle.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleRestController {

    private final SaleService saleService;

    @Autowired
    public SaleRestController(SaleService saleService, SaleRepository saleRepository) {
        this.saleService = saleService;
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    @GetMapping
    public List<Sale> getAllSales() {
        return saleService.findAll();
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    @GetMapping("/{saleId}")
    public ResponseEntity<?> getSaleById(@PathVariable Integer saleId) {
        return saleService.findSaleById(saleId);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @PostMapping
    public Sale createSale(@RequestBody Sale sale) {
        return saleService.createSale(sale);
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    @PutMapping("/{saleId}")
    public ResponseEntity<?> updateSale(@PathVariable Integer saleId, @RequestBody Sale sale) {
        return saleService.updateSale(saleId, sale);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{saleId}")
    public ResponseEntity<?> deleteSale(@PathVariable Integer saleId) {
        return saleService.deleteSale(saleId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @PostMapping("/{saleId}/products")
    public ResponseEntity<?> addProductsToSale(@PathVariable int saleId, @RequestBody List<String> productIds) {
        return saleService.addProductsToSale(saleId, productIds);
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    @DeleteMapping("/{saleId}/products")
    public ResponseEntity<?> removeProductFromSale(
            @PathVariable int saleId,
            @RequestParam String productId) {
        return saleService.removeProductFromSale(saleId, productId);
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    @GetMapping("/{saleId}/products")
    public ResponseEntity<List<Product>> getProductsInSale(
            @PathVariable int saleId) {
        return saleService.getProductsInSale(saleId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @GetMapping("/products")
    public ResponseEntity<?> getListProductInSales() {
        return saleService.getListProductsInSales();
    }
}
