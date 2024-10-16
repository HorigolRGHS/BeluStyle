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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Sale createSale(Sale sale) {
        return saleRepository.save(sale);
    }

    @Transactional
    public Sale updateSale(Sale sale) {
        return saleRepository.save(sale);
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
        return ResponseEntity.ok(responseDTO);
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
