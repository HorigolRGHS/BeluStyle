package com.emc.belustyle.repo;

import com.emc.belustyle.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}
