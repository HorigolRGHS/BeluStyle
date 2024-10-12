package com.emc.belustyle.repo;

import com.emc.belustyle.entity.StockProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockProductRepository extends JpaRepository<StockProduct, Integer> {
}
