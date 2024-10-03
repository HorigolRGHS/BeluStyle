package com.emc.belustyle.dao;

import com.emc.belustyle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
    User findByUsername(String username);
    List<User> findByFullNameContainingIgnoreCase(String fullName);
}
