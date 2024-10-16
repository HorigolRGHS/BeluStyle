package com.emc.belustyle.repo;

import com.emc.belustyle.entity.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Integer> {
}
