package com.emc.belustyle.service;

import com.emc.belustyle.repo.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {
    private final BrandRepository brandRepository;

    @Autowired
    public StatisticsService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Object[]> getTotalProductsSoldPerBrand() {
        return brandRepository.findTotalProductsSoldPerBrand();
    }
}
