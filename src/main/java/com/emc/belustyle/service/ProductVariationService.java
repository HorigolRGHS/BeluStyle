package com.emc.belustyle.service;

import com.emc.belustyle.entity.ProductVariation;
import com.emc.belustyle.repo.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductVariationService {
    private ProductVariationRepository productVariationRepository;

    @Autowired
    public ProductVariationService(ProductVariationRepository productVariationRepository) {
        this.productVariationRepository = productVariationRepository;
    }

    public ProductVariation findById(int id) {
        return productVariationRepository.findById(id).orElse(null);
    }
}
