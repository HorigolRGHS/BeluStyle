package com.emc.belustyle.repo;

import com.emc.belustyle.dto.CategoryDTO;
import com.emc.belustyle.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "SELECT c.category_id, c.category_name, c.category_description, c.image_url, " +
            "c.created_at, c.updated_at, COALESCE(SUM(sp.quantity), 0) AS total_quantity " +
            "FROM category c " +
            "LEFT JOIN product p ON c.category_id = p.category_id " +
            "LEFT JOIN product_variation pv ON p.product_id = pv.product_id " +
            "LEFT JOIN stock_product sp ON pv.variation_id = sp.variation_id " +
            "GROUP BY c.category_id, c.category_name, c.category_description, c.image_url, c.created_at, c.updated_at",
            nativeQuery = true)
    List<Object[]> findAllCategoriesWithQuantity();

}
