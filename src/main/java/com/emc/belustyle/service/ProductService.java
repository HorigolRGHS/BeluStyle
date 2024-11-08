package com.emc.belustyle.service;

import com.emc.belustyle.dto.ProductDTO;
import com.emc.belustyle.dto.ProductListDTO;
import com.emc.belustyle.entity.ProductVariation;
import com.emc.belustyle.entity.Sale;
import com.emc.belustyle.repo.*;
import com.emc.belustyle.entity.Brand;
import com.emc.belustyle.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private BrandRepository brandRepository;
    private CategoryRepository categoryRepository;
    private ProductVariationRepository productVariationRepository;
    private SizeRepository sizeRepository;
    private ColorRepository colorRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, BrandRepository brandRepository,CategoryRepository categoryRepository, ProductVariationRepository productVariationRepository, SizeRepository sizeRepository,ColorRepository colorRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.productVariationRepository = productVariationRepository;
        this.sizeRepository = sizeRepository;
        this.colorRepository = colorRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteProduct(String id) { productRepository.deleteById(id); }

    @Transactional
    public Product addProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setProductDescription(productDTO.getProductDescription());
        product.setCategory(categoryRepository.findById(productDTO.getCategoryId()).orElse(null));
         product.setBrand(brandRepository.findById(productDTO.getBrandId()).orElse(null));
        Product savedProduct = productRepository.save(product);

    List<ProductVariation> variations = new ArrayList<>();
        for (ProductDTO.ProductVariationDTO variationDTO : productDTO.getVariations()) {
            ProductVariation variation = new ProductVariation();
            variation.setSize(sizeRepository.findById(variationDTO.getSizeId()).orElse(null));
            variation.setColor(colorRepository.findById(variationDTO.getColorId()).orElse(null));
            variation.setProductPrice(variationDTO.getProductPrice());
            variation.setProductVariationImage(variationDTO.getProductVariationImage());
            variation.setProduct(product); // Set the reference back to the product
            variations.add(variation);
        }

        // Save all ProductVariations to the database
        productVariationRepository.saveAll(variations);

        // Associate the variations with the product
         savedProduct.setProductVariations(variations);

        return savedProduct;
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

    public List<ProductListDTO> getListProduct() {
        List<Object[]> result = productRepository.getListProduct();
        List<ProductListDTO> productList = new ArrayList<>();

        for (Object[] row : result) {
            String productId = (String) row[0];

            // Check if productId already exists in the list
            boolean exists = productList.stream()
                    .anyMatch(product -> product.getProductId().equals(productId));

            // If it doesn't exist, create and add the ProductListDTO
            if (!exists) {
                ProductListDTO productListDTO = new ProductListDTO();
                productListDTO.setProductId(productId);
                productListDTO.setProductName((String) row[1]);
                productListDTO.setProductDescription((String) row[2]);
                productListDTO.setBrandId((Integer) row[3]);
                productListDTO.setBrandName((String) row[4]);
                productListDTO.setCategoryId((Integer) row[5]);
                productListDTO.setCategoryName((String) row[6]);
                productListDTO.setProductVariationImage((String) row[7]);
                productListDTO.setProductPrice((BigDecimal) row[8]);
                productListDTO.setSaleType((Sale.SaleType) row[9]);
                productListDTO.setSaleValue((BigDecimal) row[10]);
                productListDTO.setAverageRating((Double) row[11]);
                productListDTO.setTotalRatings((Long) row[12]);

                productList.add(productListDTO);
            }
        }

        return productList;
    }


}
