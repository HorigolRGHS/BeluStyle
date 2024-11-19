package com.emc.belustyle.repo;

import com.emc.belustyle.entity.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.List;

@Repository
public interface ProductVariationRepository extends JpaRepository<ProductVariation, Integer> {
    @Query("SELECT pv FROM ProductVariation pv WHERE pv.product.productId = :productId")
    public List<ProductVariation> findProductVariationByProductId(@Param("productId") String productId);

    @Query("SELECT pv FROM ProductVariation pv WHERE pv.product.productId = :productId")
    List<ProductVariation> findByProductId(String productId);
}
