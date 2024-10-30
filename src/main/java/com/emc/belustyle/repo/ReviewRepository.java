package com.emc.belustyle.repo;

import com.emc.belustyle.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r FROM Review r WHERE r.productId = :productId")
    List<Review> findReviewDetails(@Param("productId") String productId);

    List<Review> findAll();
}
