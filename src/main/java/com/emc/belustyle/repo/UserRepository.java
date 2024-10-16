package com.emc.belustyle.repo;

import com.emc.belustyle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPasswordHash(String username, String passwordHash);
    Optional<User> findByGoogleId(String googleId);
    boolean existsByUsername(String username);
    boolean existsByEmail(String username);

}
