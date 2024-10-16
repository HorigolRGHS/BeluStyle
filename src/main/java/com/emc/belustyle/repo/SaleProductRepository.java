package com.emc.belustyle.repo;

import com.emc.belustyle.entity.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleProductRepository extends JpaRepository<SaleProduct, SaleProduct.SaleProductId> {
}
