package com.emc.belustyle.repo;

import com.emc.belustyle.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
    public void deleteById(int id);

}
