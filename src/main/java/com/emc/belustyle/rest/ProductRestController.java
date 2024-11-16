package com.emc.belustyle.rest;

import com.emc.belustyle.dto.ProductDTO;
import com.emc.belustyle.dto.ProductItemDTO;
import com.emc.belustyle.dto.ProductListDTO;
import com.emc.belustyle.dto.ResponseDTO;
import com.emc.belustyle.dto.mapper.ProductMapper;
import com.emc.belustyle.entity.Product;
import com.emc.belustyle.service.BrandService;
import com.emc.belustyle.service.CategoryService;
import com.emc.belustyle.service.ProductService;
import com.emc.belustyle.service.ProductVariationService;
import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
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
    public List<ProductListDTO> getAllProducts() {
        return productService.getListProduct();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @GetMapping("/{productId}/product-variations")
    @JsonView(Views.ProductView.class)
    public ResponseEntity<?> getProductVariationsByProductId(@PathVariable String productId) {
        Product product = productService.findById(productId);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductItemByProductId(@PathVariable String productId) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Product not found!");
        responseDTO.setStatusCode(404);
        ProductItemDTO product = productService.findProductItemById(productId);
        if (product != null) {
            return ResponseEntity.ok().body(product);
        }
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    @PostMapping("/{productId}/product-variations")
    public ResponseEntity<Product> addProductVariations(
            @PathVariable("productId") String productId,
            @RequestBody ProductDTO productDTO) {
        Product product = productService.addVariationsToExistingProduct(productId, productDTO.getVariations());
        return ResponseEntity.ok(product);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.addProduct(productDTO);
        return ResponseEntity.ok(product);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    @PutMapping("/{id}")
    public Product updateProduct(@RequestBody ProductDTO productDTO, @PathVariable String id) {
        return productService.updateProduct(productDTO, id);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }

}
