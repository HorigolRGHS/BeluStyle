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
    @Query("SELECT r.reviewId, u.fullName, r.reviewRating, r.reviewComment FROM Review r " +
            "left join Product p on r.productId = p.productId " +
            "left Join OrderDetail od on r.orderDetail.orderDetailId = od.orderDetailId " +
            "left join Order o on o.orderId = od.order.orderId " +
            "left join User u on u.userId = o.user.userId " +
            "WHERE p.productId = :productId")
    List<Object[]> findReviewByProductId(@Param("productId") String productId);

    @Query("SELECT COALESCE(count(r.reviewRating), 0) as totalRating, COALESCE(avg(r.reviewRating), 0) as avgRating FROM Review r join Product p on r.productId = p.productId WHERE p.productId = :productId")
    List<Object[]> countAndAvgRatingByProductId(@Param("productId") String productId);

    @Query("SELECT r FROM Review r WHERE r.productId = :productId")
    List<Review> findReviewDetails(@Param("productId") String productId);

@Query("SELECT r FROM Review r WHERE r.orderDetail.orderDetailId = :orderDetailId")
List<Review> findReviewsByOrderDetailId(@Param("orderDetailId") Integer orderDetailId);

    List<Review> findAll();


    @Query("SELECT r.reviewId, COALESCE(u.fullName, 'Anonymous') as fullName, r.reviewRating, r.reviewComment, r.productId " +
            "FROM Review r " +
            "LEFT JOIN r.orderDetail od " +
            "LEFT JOIN od.order o " +
            "LEFT JOIN o.user u " +
            "WHERE r.orderDetail.orderDetailId = :orderDetailId AND r.orderDetail.order.orderId = :orderId")
    List<Object[]> findReviewsByOrderDetailIdAndOrderId(@Param("orderDetailId") Integer orderDetailId, @Param("orderId") String orderId);


}
