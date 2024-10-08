package com.emc.belustyle.rest;

import com.emc.belustyle.dto.ProductDTO;
import com.emc.belustyle.dto.mapper.ProductMapper;
import com.emc.belustyle.entity.Brand;
import com.emc.belustyle.entity.Product;
import com.emc.belustyle.service.BrandService;
import com.emc.belustyle.service.CategoryService;
import com.emc.belustyle.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductRestController {
    private ProductService productService;
    private BrandService brandService;
    private CategoryService categoryService;

    @Autowired
    public ProductRestController(ProductService productService, BrandService brandService, CategoryService categoryService) {
        this.productService = productService;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable String productId) {
        Product product = productService.findById(productId);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    @PostMapping
    public Product createProduct(@RequestBody ProductDTO productDTO) {
        Product product = ProductMapper.INSTANCE.toEntity(productDTO);
        product.setBrand(brandService.findById(productDTO.getBrandId()));
        product.setCategory(categoryService.findById(productDTO.getCategoryId()));
        return productService.createProduct(product);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    @PutMapping("/{id}")
    public Product updateProduct(@RequestBody Product updatedProduct) {
        return productService.updateProduct(updatedProduct);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }

}