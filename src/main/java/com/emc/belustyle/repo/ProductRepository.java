package com.emc.belustyle.repo;

import com.emc.belustyle.dto.ProductListDTO;
import com.emc.belustyle.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT p.productId, p.productName, p.productDescription, " +
            "b.brandName, c.categoryName, pv.productVariationImage, pv.productPrice, " +
            "s.saleType, COALESCE(s.saleValue, 0), COALESCE(AVG(r.reviewRating), 0), COUNT(r.reviewId) " +
            "FROM Product p " +
            "LEFT JOIN p.brand b " +
            "LEFT JOIN p.category c " +
            "LEFT JOIN p.productVariations pv ON pv.variationId = (" +
            "   SELECT MIN(pv2.variationId) FROM ProductVariation pv2 WHERE pv2.product.productId = p.productId" +
            ") " +
            "LEFT JOIN SaleProduct sp ON p.productId = sp.product.productId " +
            "LEFT JOIN Sale s ON sp.sale.saleId = s.saleId AND s.saleStatus = 'ACTIVE' " +
            "LEFT JOIN Review r ON p.productId = r.productId " +
            "GROUP BY p.productId, p.productName, p.productDescription, b.brandName, c.categoryName, " +
            "pv.productVariationImage, pv.productPrice, s.saleType, s.saleValue")
    List<Object[]> getListProduct();

}
