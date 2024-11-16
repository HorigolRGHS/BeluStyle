package com.emc.belustyle.repo;

import com.emc.belustyle.entity.User;
import com.emc.belustyle.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Query("SELECT u FROM User u WHERE u.role.roleName IN :roles " +
            "AND (:search IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<User> findByRolesAndSearch(
            @Param("roles") List<UserRole.RoleName> roles,
            @Param("search") String search,
            Pageable pageable);

    // Optional query for role filtering only (used when no search term is provided)
    @Query("SELECT u FROM User u WHERE u.role.roleName IN :roles")
    Page<User> findByRolesOnly(@Param("roles") List<UserRole.RoleName> roles, Pageable pageable);

}
