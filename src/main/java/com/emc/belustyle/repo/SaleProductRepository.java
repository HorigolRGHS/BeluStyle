package com.emc.belustyle.repo;

import com.emc.belustyle.entity.Sale;
import com.emc.belustyle.entity.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SaleProductRepository extends JpaRepository<SaleProduct, SaleProduct.SaleProductId> {
    @Modifying
    @Transactional
    @Query("DELETE FROM SaleProduct sp WHERE sp.id.saleId = :saleId")
    void deleteBySaleId(@Param("saleId") Integer saleId);

    List<SaleProduct> findById_SaleId(Integer saleId);

    @Query("SELECT sp FROM SaleProduct sp join Sale s on sp.sale.saleId = s.saleId where sp.product.productId = :productId AND s.saleStatus = 'ACTIVE' ")
    SaleProduct findSaleProductByProductId(@Param("productId") String productId);
}
