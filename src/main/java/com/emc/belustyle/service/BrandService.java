package com.emc.belustyle.service;

import com.emc.belustyle.dto.SearchBrandDTO;
import com.emc.belustyle.repo.BrandRepository;
import com.emc.belustyle.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandService {

    private BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    public Brand findById(int id) {
        return brandRepository.findById(id).orElse(null);
    }

    @Transactional
    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public List<SearchBrandDTO> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream()
                .map(brand -> new SearchBrandDTO(brand.getBrandName(), brand.getBrandDescription()))
                .collect(Collectors.toList());
    }

    @Transactional
    public Brand updateBrand(Brand updatedBrand) {
        Optional<Brand> existingBrand = brandRepository.findById(updatedBrand.getBrandId());

        if (existingBrand.isPresent()) {
            Brand brand = existingBrand.get();
            brand.setBrandName(updatedBrand.getBrandName());
            brand.setBrandDescription(updatedBrand.getBrandDescription());
            brand.setLogoUrl(updatedBrand.getLogoUrl());
            brand.setWebsiteUrl(updatedBrand.getWebsiteUrl());
            return brandRepository.save(brand);
        } else {
            return null;
        }
    }

    @Transactional
    public boolean deleteBrand(int brandId) {
        if (brandRepository.existsById(brandId)) {
            brandRepository.deleteById(brandId);
            return true;
        }
        return false;
    }

}
