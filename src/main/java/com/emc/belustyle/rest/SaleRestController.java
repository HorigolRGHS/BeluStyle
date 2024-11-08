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

    private SaleService saleService;

    private SaleRepository saleRepository;

    @Autowired
    public SaleRestController(SaleService saleService, SaleRepository saleRepository) {
        this.saleService = saleService;
        this.saleRepository = saleRepository;
    }

    @GetMapping
    public List<Sale> getAllSales() {
        return saleService.findAll();
    }

    @GetMapping("/{saleId}")
    public ResponseEntity<?> getSaleById(@PathVariable Integer saleId) {
        ResponseDTO responseDTO = new ResponseDTO();
        Sale sale = saleService.findSaleById(saleId);
        if (sale == null) {
            responseDTO.setStatusCode(HttpStatus.NO_CONTENT.value());
            responseDTO.setMessage("No sale found");
            responseDTO.setStatusCode(404);
            return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
        }
        responseDTO.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.status(responseDTO.getStatusCode()).body(sale);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createSale(@RequestBody Sale sale) {
        return saleService.createSale(sale);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @PutMapping("/{saleId}")
    public ResponseEntity<ResponseDTO> updateSale(@PathVariable Integer saleId, @RequestBody Sale sale) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Sale updatedSale = saleService.updateSale(saleId, sale);
            responseDTO.setMessage("Sale updated successfully");
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException ex) {
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{saleId}")
    public ResponseEntity<ResponseDTO> deleteSale(@PathVariable Integer saleId) {
        ResponseDTO responseDTO = new ResponseDTO();
        if (!saleService.deleteSale(saleId) ) {
            responseDTO.setMessage("Sale not found");
            responseDTO.setStatusCode(404);
            return ResponseEntity.status(404).body(responseDTO);
        }
        responseDTO.setMessage("Sale deleted");
        responseDTO.setStatusCode(200);
        return ResponseEntity.status(200).body(responseDTO);

    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/{saleId}/products")
    public ResponseEntity<?> addProductsToSale(@PathVariable int saleId, @RequestBody List<String> productIds) {
        return saleService.addProductsToSale(saleId, productIds);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{saleId}/products")
    public ResponseEntity<?> removeProductFromSale(
            @PathVariable int saleId,
            @RequestParam String productId) {

        return saleService.removeProductFromSale(saleId, productId);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @GetMapping("/{saleId}/products")
    public ResponseEntity<List<Product>> getProductsInSale(
            @PathVariable int saleId) {
        return saleService.getProductsInSale(saleId);
    }
}
