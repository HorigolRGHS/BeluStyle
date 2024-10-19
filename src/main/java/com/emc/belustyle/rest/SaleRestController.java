package com.emc.belustyle.rest;

import com.emc.belustyle.dto.ResponseDTO;
import com.emc.belustyle.dto.SaleDTO;
import com.emc.belustyle.entity.Product;
import com.emc.belustyle.entity.Sale;
import com.emc.belustyle.exception.CustomException;
import com.emc.belustyle.repo.SaleRepository;
import com.emc.belustyle.service.SaleService;
import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleRestController {
    @Autowired
    private SaleService saleService;
    @Autowired
    private SaleRepository saleRepository;

    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    @GetMapping
    @JsonView(Views.OrderView.class)
    public List<Sale> getAllSales() {
        return saleService.findAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
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

    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    @PostMapping
    public ResponseEntity<?> createSale(@RequestBody Sale sale) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatusCode(HttpStatus.CREATED.value());
        Sale createdSale = saleService.createSale(sale);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(createdSale);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    @PutMapping("/{saleId}")
    public ResponseEntity<ResponseDTO> updateSale(@PathVariable Integer saleId, @RequestBody Sale sale) {
        ResponseDTO responseDTO = new ResponseDTO();

        // Check if the sale exists
        Sale existingSale = saleService.findSaleById(saleId);
        if (existingSale == null) {
            responseDTO.setMessage("Sale not found");
            responseDTO.setStatusCode(404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }

        // Update the sale properties
        existingSale.setSaleType(sale.getSaleType());
        existingSale.setSaleValue(sale.getSaleValue());
        existingSale.setStartDate(sale.getStartDate());
        existingSale.setEndDate(sale.getEndDate());
        existingSale.setSaleStatus(sale.getSaleStatus());
        existingSale.setUpdatedAt(new java.util.Date());

        // Save updated sale
        Sale updatedSale = saleService.createSale(existingSale);

        responseDTO.setMessage("Sale updated successfully");
        responseDTO.setStatusCode(200);
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
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

    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    @PostMapping("/{saleId}/products")
    public ResponseEntity<Void> addProductsToSale(@PathVariable int saleId, @RequestBody List<String> productIds) {
        saleService.addProductsToSale(saleId, productIds);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    @DeleteMapping("/{saleId}/products/{productId}")
    public ResponseEntity<Void> removeProductFromSale(
            @PathVariable int saleId,
            @PathVariable String productId) {

        saleService.removeProductFromSale(saleId, productId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{saleId}/products")
    public ResponseEntity<List<Product>> getProductsInSale(
            @PathVariable int saleId) {

        return saleService.getProductsInSale(saleId);
    }
}
