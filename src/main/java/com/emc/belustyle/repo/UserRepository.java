package com.emc.belustyle.repo;

import com.emc.belustyle.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    // Add by meow meow, if u done understand, plz ask Huy, Don't ask meow meow, thanks !!
    Optional<User> findById(String userId);

    Page<User> findByUsernameContainingOrEmailContaining(String username, String email, Pageable pageable);

}
