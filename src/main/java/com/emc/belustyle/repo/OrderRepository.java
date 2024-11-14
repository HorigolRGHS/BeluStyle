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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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


    @Query("SELECT o FROM Order o WHERE o.user.userId = :userId")
    Page<Order> findByUserId(@Param("userId") String userId, Pageable pageable);
    @Modifying
    @Query("UPDATE Order o SET o.orderStatus = 'CANCELLED' WHERE o.orderId = :orderId")
    void updateOrderStatusToCancelled(@Param("orderId") String orderId);

    @Query("SELECT o FROM Order o left join User u on u.username = o.user.username WHERE u.username = :username AND o.orderId = :orderId ")
    Optional<Order> findOrderByUserIdAndOrderId(String username, String orderId);

}
