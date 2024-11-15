package com.emc.belustyle.repo;

import com.emc.belustyle.dto.ProductReviewDTO;
import com.emc.belustyle.dto.RatingSummaryDTO;
import com.emc.belustyle.entity.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r.reviewId, COALESCE(u.fullName, 'Anonymous') as fullName, r.reviewRating, r.reviewComment " +
            "FROM Review r " +
            "LEFT JOIN r.orderDetail od " +
            "LEFT JOIN od.order o " +
            "LEFT JOIN o.user u " +
            "WHERE r.productId = :productId")
    List<Object[]> findReviewByProductId(@Param("productId") String productId);



    @Query("SELECT COUNT(r.reviewRating) AS totalRating, COALESCE(AVG(r.reviewRating), 0) AS avgRating " +
            "FROM Review r " +
            "WHERE r.productId = :productId")
    List<Object[]> countAndAvgRatingByProductId(@Param("productId") String productId);


    @Query("SELECT r.reviewId, COALESCE(u.fullName, 'Anonymous') as fullName, r.reviewRating, r.reviewComment, r.productId " +
            "FROM Review r " +
            "LEFT JOIN r.orderDetail od " +
            "LEFT JOIN od.order o " +
            "LEFT JOIN o.user u " +
            "WHERE r.orderDetail.orderDetailId = :orderDetailId AND r.orderDetail.order.orderId = :orderId")
    List<Object[]> findReviewsByOrderDetailIdAndOrderId(@Param("orderDetailId") Integer orderDetailId, @Param("orderId") String orderId);


}
