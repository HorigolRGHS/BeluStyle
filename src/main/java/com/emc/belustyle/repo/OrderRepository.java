package com.emc.belustyle.repo;

import com.emc.belustyle.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Query(value = "SELECT * FROM `order`", nativeQuery = true)
    List<Order> findAll();
}

