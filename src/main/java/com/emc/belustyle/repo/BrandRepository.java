package com.emc.belustyle.repo;

import com.emc.belustyle.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BrandRepository extends JpaRepository<Brand, Integer> {
    List<Brand> findByBrandNameContainingIgnoreCase(String brandName);
}
