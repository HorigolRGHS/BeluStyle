package com.emc.belustyle.service;

import com.emc.belustyle.repo.ProductRepository;
import com.emc.belustyle.entity.Brand;
import com.emc.belustyle.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteProduct( String id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Product updatedProduct) {
        Optional<Product> existingProduct = productRepository.findById(updatedProduct.getProductId());

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setProductDescription(updatedProduct.getProductDescription());
            product.setProductName(updatedProduct.getProductName());
            product.setProductDescription(updatedProduct.getProductDescription());
            product.setBrand(updatedProduct.getBrand());
            product.setCategory(updatedProduct.getCategory());
            return productRepository.save(product);
        } else {
            return null;
        }
    }

}
