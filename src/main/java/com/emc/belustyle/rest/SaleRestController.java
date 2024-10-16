package com.emc.belustyle.rest;

import com.emc.belustyle.dto.ResponseDTO;
import com.emc.belustyle.entity.Sale;
import com.emc.belustyle.exception.CustomException;
import com.emc.belustyle.repo.SaleRepository;
import com.emc.belustyle.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleRestController {
    @Autowired
    private SaleService saleService;
    @Autowired
    private SaleRepository saleRepository;

    @GetMapping
    public List<Sale> getAllSales() {
        return saleService.findAll();
    }

    @GetMapping("/{saleId}")
    public ResponseEntity<ResponseDTO> getSaleById(@PathVariable Integer saleId) {
        ResponseDTO responseDTO = new ResponseDTO();
        Sale sale = saleService.findSaleById(saleId);
        if (sale == null) {
            responseDTO.setStatusCode(HttpStatus.NO_CONTENT.value());
            responseDTO.setMessage("No sale found");
            responseDTO.setStatusCode(404);
            return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
        }
        responseDTO.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<Sale> createSale(@RequestBody Sale sale) {
        Sale createdSale = saleService.createSale(sale);
        return ResponseEntity.ok(createdSale);
    }

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

    @PostMapping("/{saleId}/products")
    public ResponseEntity<Void> addProductsToSale(@PathVariable int saleId, @RequestBody List<String> productIds) {
        saleService.addProductsToSale(saleId, productIds);
        return ResponseEntity.ok().build();
    }
    }
