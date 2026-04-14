package com.zorro.usermanagement.repository;

import com.zorro.usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // Find by email (derived query)
    Optional<User> findByEmail(String email);

    // Find by email or username (derived query)
    Optional<User> findByEmailOrUsername(String email, String username);

    // Find by username (derived query)
    Optional<User> findByUsername(String username);

    // Find all users by city (derived query)
    List<User> findByCity(String city);

    // Find all users by status (derived query)
    List<User> findByStatus(User.UserStatus status);

    // Find by country and status using JPQL
    @Query("SELECT u FROM User u WHERE u.country = :country AND u.status = :status")
    List<User> findByCountryAndStatus(@Param("country") String c,@Param("status") User.UserStatus s);

    // Full-text search across name, email, username using native SQL
    @Query(value = "SELECT * FROM users WHERE " +
                   "LOWER(first_name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
                   "LOWER(last_name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
                   "LOWER(email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
                   "LOWER(username) LIKE LOWER(CONCAT('%', :query, '%'))",
           nativeQuery = true)
    List<User> searchUsers(@Param("query") String query);

    // Check existence by email or username
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
