package com.emc.belustyle.repo;

import com.emc.belustyle.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository


public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {

    @Query("SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.user u " +
            "LEFT JOIN FETCH o.staff s " +
            "WHERE (:status IS NULL OR o.orderStatus = :status) " +
            "AND (:userId IS NULL OR o.user.userId = :userId) " +
            "ORDER BY o.orderDate DESC")
    Page<Order> findAllByStatusAndUserId(String status, String userId, Pageable pageable);


    @Query("SELECT o FROM Order o WHERE o.user.userId = :userId ORDER BY o.orderDate DESC")
    Page<Order> findByUserId(@Param("userId") String userId, Pageable pageable);
    @Modifying
    @Query("UPDATE Order o SET o.orderStatus = 'CANCELLED' WHERE o.orderId = :orderId")
    void updateOrderStatusToCancelled(@Param("orderId") String orderId);

    @Query("SELECT o FROM Order o left join User u on u.username = o.user.username WHERE u.username = :username AND o.orderId = :orderId ")
    Optional<Order> findOrderByUserIdAndOrderId(String username, String orderId);

    //Statistics

        @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderStatus = 'COMPLETED'")
        Double findTotalRevenue();

        @Query("SELECT o.orderDate, SUM(o.totalAmount) FROM Order o WHERE o.orderStatus = 'COMPLETED' GROUP BY o.orderDate ORDER BY o.orderDate ASC")
        List<Object[]> findDailyRevenue();

    @Query(value = """
        SELECT 
            YEAR(o.order_date) AS year,
            MONTH(o.order_date) AS month,
            SUM(o.total_amount) AS totalAmount
        FROM `order` o
        WHERE o.order_status = 'COMPLETED'
        GROUP BY YEAR(o.order_date), MONTH(o.order_date)
        ORDER BY year, month
        """, nativeQuery = true)
    List<Object[]> findMonthlyRevenue();


    @Query("SELECT o.orderStatus, COUNT(o) FROM Order o GROUP BY o.orderStatus")
    List<Object[]> findOrderStatusStatistics();

    @Query(value = """
        SELECT 
            pv.product_id AS productId,
            p.product_name AS productName,
            SUM(od.order_quantity) AS totalSold
        FROM order_detail od
        JOIN product_variation pv ON od.variation_id = pv.variation_id
        JOIN product p ON pv.product_id = p.product_id
        GROUP BY pv.product_id, p.product_name
        ORDER BY totalSold DESC
        """, nativeQuery = true)
    List<Object[]> findBestSellingProducts();

    @Query(value = """
    SELECT 
        p.product_id AS productId, 
        p.product_name AS productName, 
        SUM(od.order_quantity) AS totalQuantitySold
    FROM 
        `order` o
    JOIN 
        order_detail od ON o.order_id = od.order_id
    JOIN 
        product_variation pv ON od.variation_id = pv.variation_id    
    JOIN 
        product p ON pv.product_id = p.product_id
    WHERE 
        o.order_status = 'COMPLETED' AND DATE_FORMAT(o.order_date, '%Y-%m') = :month
    GROUP BY 
        p.product_id, p.product_name
    ORDER BY 
        totalQuantitySold DESC
    """, nativeQuery = true)
    List<Object[]> findBestSellingByMonth(@Param("month") String month);

    @Query(value = """
    SELECT 
        o.order_status AS orderStatus, 
        COUNT(o.order_id) AS totalOrders
    FROM 
        `order` o
    WHERE 
        DATE_FORMAT(o.order_date, '%Y-%m') = :month
    GROUP BY 
        o.order_status
    ORDER BY 
        totalOrders DESC
    """, nativeQuery = true)
    List<Object[]> findOrderStatusByMonth(@Param("month") String month);

}
