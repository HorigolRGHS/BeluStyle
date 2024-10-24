package com.emc.belustyle.service;

import com.emc.belustyle.dto.ResponseDTO;
import com.emc.belustyle.dto.SaleDTO;
import com.emc.belustyle.entity.Product;
import com.emc.belustyle.entity.Sale;
import com.emc.belustyle.entity.SaleProduct;
import com.emc.belustyle.repo.ProductRepository;
import com.emc.belustyle.repo.SaleProductRepository;
import com.emc.belustyle.repo.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final SaleProductRepository saleProductRepository;

    public SaleService(SaleRepository saleRepository, ProductRepository productRepository, SaleProductRepository saleProductRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.saleProductRepository = saleProductRepository;
    }

    @Transactional
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    @Transactional
    public ResponseEntity<?> createSale(Sale sale) {
        ResponseDTO responseDTO = new ResponseDTO();

        if (sale.getSaleType() == Sale.SaleType.PERCENTAGE && sale.getSaleValue().compareTo(new BigDecimal("100")) >= 0) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setMessage("Sale value must be less than 100 for percentage sale type");
            return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
        }

        Sale createdSale = saleRepository.save(sale);
        responseDTO.setStatusCode(HttpStatus.CREATED.value());
        return ResponseEntity.status(responseDTO.getStatusCode()).body(createdSale);
    }

    @Transactional
    public Sale updateSale(Integer saleId, Sale saleData) {
        Sale existingSale = saleRepository.findById(saleId)
                .orElseThrow(() -> new RuntimeException("Sale not found"));

        // Update the sale properties
        existingSale.setSaleType(saleData.getSaleType());
        existingSale.setSaleValue(saleData.getSaleValue());
        existingSale.setStartDate(saleData.getStartDate());
        existingSale.setEndDate(saleData.getEndDate());
        existingSale.setSaleStatus(saleData.getSaleStatus());
        existingSale.setUpdatedAt(new java.util.Date());

        return saleRepository.save(existingSale);
    }

    @Transactional
    public boolean deleteSale(Integer saleId) {
        if (!saleRepository.existsById(saleId)) {
            return false;
        }

        // Remove associated SaleProduct entries
        saleProductRepository.deleteBySaleId(saleId);

        // Remove the sale
        saleRepository.deleteById(saleId);

        return true;
    }

        @Transactional
    public Sale findSaleById(Integer saleId) {
        return saleRepository.findById(saleId).orElse(null);
    }

    @Transactional
    public ResponseEntity<ResponseDTO> addProductsToSale(int saleId, List<String> productIds) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Sale sale = saleRepository.findById(saleId).orElseThrow(() -> new RuntimeException("Sale not found"));
            responseDTO.setMessage("Sale not found");
            responseDTO.setStatusCode(404);
            for (String productId : productIds) {
                Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

                SaleProduct.SaleProductId id = new SaleProduct.SaleProductId();
                id.setSaleId(saleId);
                id.setProductId(productId);

                SaleProduct saleProduct = new SaleProduct();
                saleProduct.setId(id);
                saleProduct.setSale(sale);
                saleProduct.setProduct(product);
                saleProductRepository.save(saleProduct);
                responseDTO.setStatusCode(HttpStatus.CREATED.value());
                responseDTO.setMessage("Sale added successfully");
            }
        }catch (RuntimeException e) {
            responseDTO.setMessage("Product not found");
            responseDTO.setStatusCode(404);
        }
        return ResponseEntity.ok(responseDTO);
    }

    @Transactional
    public ResponseEntity<ResponseDTO> removeProductFromSale(int saleId, String productId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Sale sale = saleRepository.findById(saleId)
                    .orElseThrow(() -> new RuntimeException("Sale not found"));

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            SaleProduct.SaleProductId id = new SaleProduct.SaleProductId();
            id.setSaleId(saleId);
            id.setProductId(productId);

            Optional<SaleProduct> existingSaleProductOpt = saleProductRepository.findById(id);

            if (existingSaleProductOpt.isPresent()) {
                saleProductRepository.delete(existingSaleProductOpt.get());
                responseDTO.setMessage("Product removed successfully from sale.");
                responseDTO.setStatusCode(200);
            } else {
                responseDTO.setMessage("Product not found in this sale.");
                responseDTO.setStatusCode(404);
            }
        } catch (RuntimeException e) {
            responseDTO.setMessage(e.getMessage());
            responseDTO.setStatusCode(404);
        }
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @Transactional
    public ResponseEntity<List<Product>> getProductsInSale(int saleId) {
        try {
            Sale sale = saleRepository.findById(saleId)
                    .orElseThrow(() -> new RuntimeException("Sale not found"));

            List<SaleProduct> saleProducts = saleProductRepository.findById_SaleId(saleId);

            List<Product> products = saleProducts.stream()
                    .map(SaleProduct::getProduct)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(products);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

}
