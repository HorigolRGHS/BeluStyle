package com.emc.belustyle.repo;

import com.emc.belustyle.dto.ProductListDTO;
import com.emc.belustyle.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT p.productId, p.productName, p.productDescription, " +
            "b.brandId, b.brandName, c.categoryId, c.categoryName, pv.productVariationImage, pv.productPrice, " +
            "s.saleType, COALESCE(s.saleValue, 0), COALESCE(AVG(r.reviewRating), 0), COUNT(r.reviewId), " +
            "COALESCE(sp2.quantity, 0) " + // Add quantity from stock_product table
            "FROM Product p " +
            "LEFT JOIN p.brand b " +
            "LEFT JOIN p.category c " +
            "LEFT JOIN p.productVariations pv ON pv.variationId = (" +
            "   SELECT MIN(pv2.variationId) FROM ProductVariation pv2 WHERE pv2.product.productId = p.productId" +
            ") " +
            "LEFT JOIN SaleProduct sp ON p.productId = sp.product.productId " +
            "LEFT JOIN Sale s ON sp.sale.saleId = s.saleId AND s.saleStatus = 'ACTIVE' " +
            "LEFT JOIN Review r ON p.productId = r.productId " +
            "LEFT JOIN StockProduct sp2 ON pv.variationId = sp2.productVariation.variationId " + // Join stock_product table
            "GROUP BY p.productId, p.productName, p.productDescription, b.brandId, b.brandName, c.categoryId, c.categoryName, " +
            "pv.productVariationImage, pv.productPrice, s.saleType, s.saleValue, sp2.quantity " + // Include quantity in GROUP BY
            "ORDER BY p.createdAt DESC")
    List<Object[]> getListProduct();

    @Query("SELECT p.productName FROM Product p left join ProductVariation pv on p.productId = pv.product.productId WHERE pv.variationId = :variationId")
    String getProductNameById(@Param("variationId") Integer variationId);

    @Query(value = "SELECT " +
            "    months.month, " +
            "    IFNULL(SUM(od.order_quantity * (od.unit_price - od.discount_amount)), 0) AS total_revenue " +
            "FROM " +
            "    (SELECT DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL n MONTH), '%Y-%m') AS month " +
            "     FROM (SELECT 0 AS n UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION " +
            "                  SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION " +
            "                  SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11) AS months " +
            "    ) months " +
            "LEFT JOIN " +
            "    `order` o ON DATE_FORMAT(o.order_date, '%Y-%m') = months.month " +
            "LEFT JOIN " +
            "    order_detail od ON o.order_id = od.order_id AND o.order_status = 'COMPLETED' " +
            "GROUP BY " +
            "    months.month " +
            "ORDER BY " +
            "    months.month", nativeQuery = true)
    List<Object[]> findTotalRevenuePerMonth();
}
