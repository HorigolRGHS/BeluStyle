package com.emc.belustyle.repo;

import com.emc.belustyle.entity.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface SaleProductRepository extends JpaRepository<SaleProduct, SaleProduct.SaleProductId> {
    @Modifying
    @Transactional
    @Query("DELETE FROM SaleProduct sp WHERE sp.id.saleId = :saleId")
    void deleteBySaleId(@Param("saleId") Integer saleId);

    @Query("SELECT sp FROM SaleProduct sp WHERE sp.id.productId = :productId " +
            "AND sp.id.saleId != :currentSaleId " +
            "AND sp.sale.startDate <= :endPeriod " +
            "AND sp.sale.endDate >= :startPeriod " +
            "AND sp.sale.saleStatus = 'ACTIVE' ")
    List<SaleProduct> findConflictingSales(@Param("productId") String productId,
                                           @Param("currentSaleId") Integer currentSaleId,
                                           @Param("startPeriod") Date startPeriod,
                                           @Param("endPeriod") Date endPeriod);

    List<SaleProduct> findById_SaleId(Integer saleId);
}
