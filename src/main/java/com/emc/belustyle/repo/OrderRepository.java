package com.emc.belustyle.repo;

import com.emc.belustyle.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    // Truy vấn để lấy danh sách tất cả các đơn hàng, bao gồm tổng số lượng sản phẩm trong từng đơn hàng
    @Query(value = "SELECT o.order_id, o.order_date, o.total_amount, o.order_status, o.shipping_method, " +
            "o.payment_method, o.tracking_number, o.notes, o.discount_code, o.billing_address, " +
            "o.expected_delivery_date, o.transaction_reference, o.user_address, " +
            "COALESCE(SUM(od.order_quantity), 0) AS total_quantity " +
            "FROM `order` o " +  // Sửa lại để bao quanh `order` bằng dấu backticks
            "LEFT JOIN order_detail od ON o.order_id = od.order_id " +
            "GROUP BY o.order_id, o.order_date, o.total_amount, o.order_status, o.shipping_method, " +
            "o.payment_method, o.tracking_number, o.notes, o.discount_code, o.billing_address, " +
            "o.expected_delivery_date, o.transaction_reference, o.user_address",
            nativeQuery = true)
    List<Object[]> findAllOrdersWithQuantity();

    // Truy vấn để đếm số lượng đơn hàng với ID cụ thể
    @Query(value = "SELECT COUNT(*) FROM `order` WHERE order_id = ?1", nativeQuery = true)
    Long countById(String id);

    @Query("SELECT MAX(o.orderId) FROM Order o")
    String findMaxOrderId();

    @Query("SELECT o FROM Order o WHERE o.user.userId = :userId")
    List<Order> findOrdersByUserId(@Param("userId") String userId);

    @Query("SELECT o FROM Order o WHERE " +
            "(:status IS NULL OR o.orderStatus = :status) AND " +
            "(:payment IS NULL OR o.paymentMethod = :payment) AND " +
            "(:userId IS NULL OR o.user.userId = :userId) AND " +
            "(:staffId IS NULL OR o.staff.userId = :staffId OR o.staff IS NULL)")
    List<Order> filterOrders(@Param("status") String status,
                             @Param("payment") String payment,
                             @Param("userId") String userId,
                             @Param("staffId") String staffId,
                             Pageable pageable);

}

