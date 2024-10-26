package com.emc.belustyle.repo;

import com.emc.belustyle.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
    List<Sale> findAllByOrderBySaleIdDesc();
}
