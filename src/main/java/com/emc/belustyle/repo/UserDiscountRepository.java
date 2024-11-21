package com.emc.belustyle.repo;

import com.emc.belustyle.entity.UserDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDiscountRepository extends JpaRepository<UserDiscount, Integer> {

    List<UserDiscount> findAllByDiscount_DiscountId(int discountId);

    UserDiscount findByDiscount_DiscountId(int discountId);

    List<UserDiscount> findAllByUser_UserId(String userId);

    List<UserDiscount> findAllByUser_Username(String username);

    Optional<UserDiscount> findByUser_UserIdAndDiscount_DiscountId(String userId, Integer discountId);

    @Query("SELECT COUNT(ud) FROM UserDiscount ud WHERE ud.id.userId = :userId AND ud.id.discountId = :discountId")
    int countByUserIdAndDiscountId(@Param("userId") String userId, @Param("discountId") Integer discountId);

    @Query("SELECT COUNT(ud) FROM UserDiscount ud WHERE ud.discount.discountId = :discountId")
    int countByDiscountId(@Param("discountId") Integer discountId);


}
