package com.emc.belustyle.repo;

import com.emc.belustyle.entity.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleProductRepository extends JpaRepository<SaleProduct, Integer> {
}
