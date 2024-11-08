package com.emc.belustyle.service;

import com.emc.belustyle.dto.*;
import com.emc.belustyle.entity.*;
import com.emc.belustyle.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductVariationRepository productVariationRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final SaleProductRepository saleProductRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, BrandRepository brandRepository, CategoryRepository categoryRepository, ProductVariationRepository productVariationRepository, SizeRepository sizeRepository, ColorRepository colorRepository, SaleProductRepository saleProductRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.productVariationRepository = productVariationRepository;
        this.sizeRepository = sizeRepository;
        this.colorRepository = colorRepository;
        this.saleProductRepository = saleProductRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public ProductItemDTO findProductItemById(String id) {
        ProductItemDTO productItemDTO = new ProductItemDTO();
        Optional<Product> product = productRepository.findById(id);
        SaleProduct saleProduct = saleProductRepository.findSaleProductByProductId(id);
        List<Object[]> review = reviewRepository.findReviewByProductId(id);
        List<ProductReviewDTO> productReviewDTOList = new ArrayList<>();
        for (Object[] result : review) {
            ProductReviewDTO productReviewDTO = new ProductReviewDTO();
            productReviewDTO.setReviewId((int) result[0]);
            productReviewDTO.setFullName((String) result[1]);
            productReviewDTO.setReviewRating((int) result[2]);
            productReviewDTO.setReviewComment((String) result[3]);
            productReviewDTOList.add(productReviewDTO);
        }
        List<Object[]> totalRating = reviewRepository.countAndAvgRatingByProductId(id);
        List<ProductVariation> productVariations = productVariationRepository.findProductVariationByProductId(id);
        List<ProductVariationDTO> productVariationDTOList = new ArrayList<>();
        for (ProductVariation productVariation : productVariations) {
            ProductVariationDTO productVariationDTO = new ProductVariationDTO();
            productVariationDTO.setId(productVariation.getVariationId());
            productVariationDTO.setPrice(productVariation.getProductPrice());
            productVariationDTO.setImages(productVariation.getProductVariationImage());
            productVariationDTOList.add(productVariationDTO);
        }
        List<Size> sizes = sizeRepository.findAllByProductId(id);
        List<Color> colors = colorRepository.findAllByProductId(id);
        Map<String, Map<String, ProductVariationDTO>> variations = new HashMap<>();

        if (product.isPresent()) {
            for (Color color : colors) {
                Map<String, ProductVariationDTO> sizeMap = new HashMap<>();
                for (Size size : sizes) {
                    productVariations.stream()
                            .filter(variation -> variation.getColor().equals(color) && variation.getSize().equals(size))
                            .findFirst()
                            .ifPresent(variation -> {
                                ProductVariationDTO productVariationDTO = new ProductVariationDTO();
                                productVariationDTO.setId(variation.getVariationId());
                                productVariationDTO.setPrice(variation.getProductPrice());
                                productVariationDTO.setImages(variation.getProductVariationImage());
                                sizeMap.put(size.getSizeName(), productVariationDTO);
                            });
                }
                variations.put(color.getColorName(), sizeMap);
            }

            productItemDTO.setProductId(id);
            productItemDTO.setProductName(product.get().getProductName());
            productItemDTO.setDescription(product.get().getProductDescription());

            if (saleProduct != null) {
                productItemDTO.setSaleType(saleProduct.getSale().getSaleType());
                productItemDTO.setSaleValue(saleProduct.getSale().getSaleValue());
            }

            productItemDTO.setReviews(productReviewDTOList);

            if (totalRating != null) {
                for (Object[] result : totalRating) {
                    productItemDTO.setTotalRating((long) result[0]);
                    productItemDTO.setAvgRating((double) result[1]);
                }
            }

            productItemDTO.setSizes(sizes);
            productItemDTO.setColors(colors);
            productItemDTO.setVariations(variations);
        }

        return productItemDTO;
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
