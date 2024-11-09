package com.emc.belustyle.repo;

import com.emc.belustyle.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {
    @Query("SELECT s FROM Size s join ProductVariation pv on s.sizeId = pv.size.sizeId WHERE pv.product.productId = :productId")
    List<Size> findAllByProductId(@Param("productId") String productId);
}
