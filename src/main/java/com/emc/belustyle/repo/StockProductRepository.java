package com.emc.belustyle.repo;

import com.emc.belustyle.entity.StockProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockProductRepository extends JpaRepository<StockProduct, StockProduct.StockProductId> {

    @Query("SELECT sp FROM StockProduct sp JOIN FETCH sp.productVariation pv " +
            "JOIN FETCH pv.product p " +
            "JOIN FETCH p.category c " +
            "JOIN FETCH p.brand b " +
            "JOIN FETCH pv.size s " +
            "JOIN FETCH pv.color co " +
            "WHERE sp.stock.id = :stockId")
    List<StockProduct> findAllByStockId(@Param("stockId") int stockId);

    @Query("SELECT sp FROM StockProduct sp join ProductVariation pv on sp.productVariation.variationId = pv.variationId WHERE pv.variationId = :variationId")
    List<StockProduct> findStockProductByProductVariationId(@Param("variationId") int variationId);
}

