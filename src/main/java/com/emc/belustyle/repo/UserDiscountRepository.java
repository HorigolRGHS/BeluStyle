package com.emc.belustyle.repo;

import com.emc.belustyle.entity.UserDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDiscountRepository extends JpaRepository<UserDiscount, Integer> {
    List<UserDiscount> findAllByDiscountId(int discountId);
    List<UserDiscount> findAllByUserId(String userId);
    Optional<UserDiscount> findByUserIdAndDiscountId(String userId, Integer discountId);
}
