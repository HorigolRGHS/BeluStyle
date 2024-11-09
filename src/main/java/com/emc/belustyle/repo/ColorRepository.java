package com.emc.belustyle.repo;

import com.emc.belustyle.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    @Query("SELECT c FROM Color c left join ProductVariation pv on c.colorId = pv.color.colorId WHERE pv.product.productId = :productId")
    public List<Color> findAllByProductId(@Param("productId") String productId);
}
