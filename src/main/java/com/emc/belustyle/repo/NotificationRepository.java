package com.emc.belustyle.repo;

import com.emc.belustyle.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query(value = "SELECT n.title, n.message FROM notification n JOIN user_role ur" +
            " ON n.target_role_id = ur.role_id" +
            " WHERE n.target_role_id = :roleId", nativeQuery = true)
    List<Object[]> findByRoleId(@Param("roleId") int roleId);

    List<Notification> findAllByOrderByNotificationIdDesc();
}
