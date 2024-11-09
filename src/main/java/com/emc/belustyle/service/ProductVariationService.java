package com.emc.belustyle.service;

import com.emc.belustyle.dto.ProductDTO;
import com.emc.belustyle.entity.ProductVariation;
import com.emc.belustyle.repo.ColorRepository;
import com.emc.belustyle.repo.ProductVariationRepository;
import com.emc.belustyle.repo.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductVariationService {
    private ProductVariationRepository productVariationRepository;
    private ColorRepository colorRepository;
    private SizeRepository sizeRepository;

    @Autowired
    public ProductVariationService(ProductVariationRepository productVariationRepository, ColorRepository colorRepository, SizeRepository sizeRepository) {
        this.productVariationRepository = productVariationRepository;
        this.colorRepository = colorRepository;
        this.sizeRepository = sizeRepository;
    }

    public ProductVariation findById(int id) {
        return productVariationRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean deleteById(int id) {
        if(productVariationRepository.existsById(id)) {
            productVariationRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Transactional
    public ProductVariation updateProductVariation(int variationId, ProductDTO.ProductVariationDTO variationDTO) {
        ProductVariation variation = productVariationRepository.findById(variationId).orElseThrow(() -> new ResourceNotFoundException("Product Variation not found"));

        variation.setSize(sizeRepository.findById(variationDTO.getSizeId()).orElse(null));
        variation.setColor(colorRepository.findById(variationDTO.getColorId()).orElse(null));
        variation.setProductPrice(variationDTO.getProductPrice());
        variation.setProductVariationImage(variationDTO.getProductVariationImage());

        return productVariationRepository.save(variation);
    }
}
