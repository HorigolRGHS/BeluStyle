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
}
