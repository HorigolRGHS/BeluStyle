package com.emc.belustyle.service;

import com.emc.belustyle.dto.SaleProductDTO;
import com.emc.belustyle.entity.Product;
import com.emc.belustyle.entity.Sale;
import com.emc.belustyle.entity.SaleProduct;
import com.emc.belustyle.repo.ProductRepository;
import com.emc.belustyle.repo.SaleProductRepository;
import com.emc.belustyle.repo.SaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleProductRepository saleProductRepository;

    @Transactional
    public Sale createSale(SaleProductDTO saleProductDTO) {
        // Create new Sale
        Sale sale = new Sale();
        sale.setSaleType(saleProductDTO.getSaleType());
        sale.setSaleValue(saleProductDTO.getSaleValue());
        sale.setStartDate(saleProductDTO.getStartDate());
        sale.setEndDate(saleProductDTO.getEndDate());
        sale.setSaleStatus(saleProductDTO.getSaleStatus());
        sale.setCreatedAt(new Date());
        saleRepository.save(sale);

        // Create SaleProduct entries
        for (Integer productId : saleProductDTO.getProductIds()) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            SaleProduct saleProduct = new SaleProduct(sale, product);
            saleProductRepository.save(saleProduct);
        }
        return sale;
    }
}
