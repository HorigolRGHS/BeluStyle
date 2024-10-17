package com.emc.belustyle.repo;

import com.emc.belustyle.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query(value = "SELECT b.brand_id, b.brand_name, b.brand_description, b.logo_url, " +
            "b.website_url, b.created_at, b.updated_at, COALESCE(SUM(sp.quantity), 0) AS total_quantity " +
            "FROM brand b " +
            "LEFT JOIN product p ON b.brand_id = p.brand_id " +
            "LEFT JOIN product_variation pv ON p.product_id = pv.product_id " +
            "LEFT JOIN stock_product sp ON pv.variation_id = sp.variation_id " +
            "GROUP BY b.brand_id, b.brand_name, b.brand_description, b.logo_url, b.website_url, b.created_at, b.updated_at",
            nativeQuery = true)
    List<Object[]> findAllBrandsWithQuantity();
}
