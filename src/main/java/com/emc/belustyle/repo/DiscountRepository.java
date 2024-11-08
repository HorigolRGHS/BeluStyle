package com.emc.belustyle.repo;

import com.emc.belustyle.entity.Discount;
import com.emc.belustyle.entity.UserDiscount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    Optional<Discount> findByDiscountCode(String discountCode);

    Page<Discount> findAllByDiscountStatus(Discount.DiscountStatus status, Pageable pageable);

    @Query("SELECT d FROM Discount d WHERE " +
            "(:status IS NULL OR d.discountStatus = :status) AND " +
            "(:type IS NULL OR d.discountType = :type) AND " +
            "(:minOrderValue IS NULL OR d.minimumOrderValue <= :minOrderValue)")
    List<Discount> filterDiscounts(@Param("status") Discount.DiscountStatus status,
                                   @Param("type") Discount.DiscountType type,
                                   @Param("minOrderValue") Double minOrderValue,
                                   Pageable pageable);
}
