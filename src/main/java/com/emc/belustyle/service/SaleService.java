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
        return saleRepository.findAllByOrderBySaleIdDesc();
    }

    @Transactional
    public Sale createSale(Sale sale) {
        return saleRepository.save(sale);
    }

    @Transactional
    public ResponseEntity<?> updateSale(Integer saleId, Sale saleData) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Updated sale Successfully");
        Sale existingSale = saleRepository.findById(saleId).orElse(null);

        // Update the sale properties
        if (existingSale == null) {
            responseDTO.setMessage("Sale not found");
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
        }
        existingSale.setSaleType(saleData.getSaleType());
        existingSale.setSaleValue(saleData.getSaleValue());
        existingSale.setStartDate(saleData.getStartDate());
        existingSale.setEndDate(saleData.getEndDate());
        existingSale.setSaleStatus(saleData.getSaleStatus());
        existingSale.setUpdatedAt(new java.util.Date());
        Sale EditedSale = saleRepository.save(existingSale);

        return ResponseEntity.ok().body(EditedSale);
    }

    @Transactional
    public ResponseEntity<?> deleteSale(Integer saleId) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Delete sale successfully");
        if (!saleRepository.existsById(saleId)) {
            responseDTO.setMessage("Sale not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }

        saleProductRepository.deleteBySaleId(saleId);

        saleRepository.deleteById(saleId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

        @Transactional
    public ResponseEntity<?> findSaleById(Integer saleId) {
        ResponseDTO responseDTO = new ResponseDTO();
            Sale sale = saleRepository.findById(saleId).orElse(null);
            if (sale == null) {
                responseDTO.setMessage("Sale not found");
                responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
            }
        return ResponseEntity.ok().body(sale);
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

    @Transactional
    public ResponseEntity<?> getListProductsInSales() {
        return ResponseEntity.status(HttpStatus.OK).body(saleProductRepository.getAllProductsInSales());
    }

}
