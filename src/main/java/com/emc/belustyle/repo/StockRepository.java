package com.emc.belustyle.repo;

import com.emc.belustyle.dto.StockDetailDTO;
import com.emc.belustyle.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {


    @Query(value = "SELECT " +
            "s.stock_id, " +
            "s.stock_name, " +
            "s.stock_address, " +
            "pv.variation_id, " +
            "pv.product_price, " +
            "pv.product_variation_image, " +
            "pr.product_name, " +
            "pr.product_id, " +
            "br.brand_id, " +
            "br.brand_name, " +
            "ct.category_id, " +
            "ct.category_name, " +
            "sz.size_name, " +
            "cl.color_name, " +
            "cl.hex_code, " +
            "sp.quantity " +
            "FROM stock s " +
            "LEFT JOIN stock_product sp ON s.stock_id = sp.stock_id " +
            "LEFT JOIN product_variation pv ON sp.variation_id = pv.variation_id " +
            "LEFT JOIN product pr ON pv.product_id = pr.product_id " +
            "LEFT JOIN brand br ON pr.brand_id = br.brand_id " +
            "LEFT JOIN category ct ON pr.category_id = ct.category_id " +
            "LEFT JOIN size sz ON pv.size_id = sz.size_id " +
            "LEFT JOIN color cl ON pv.color_id = cl.color_id " +
            "WHERE s.stock_id = :stockId", nativeQuery = true)
    List<Object[]> findStockDetailsById(@Param("stockId") Integer stockId);


}
