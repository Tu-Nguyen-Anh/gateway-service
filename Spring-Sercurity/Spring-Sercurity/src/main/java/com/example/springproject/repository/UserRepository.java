package com.example.springproject.repository;

import com.example.springproject.dto.response.ProductResponse;
import com.example.springproject.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repository interface for managing User entities. Extends the BaseRepository interface.
 */
public interface UserRepository extends BaseRepository<User>{

    /**
     * Finds a user by the provided username.
     *
     * @param username The username of the user to find.
     * @return An Optional containing the user if found, otherwise empty.
     */
    Optional<User> findUserByUsername(String username);

    /**
     * Custom query to retrieve a Page of UserResponse objects with specific fields.
     *
     * @param pageable Pageable configuration for pagination.
     * @return A Page of UserResponse objects.
     */
    @Query("""
              select new com.example.springproject.dto.response.ProductResponse
              (u.id, u.username,u.email,u.phone,u.role,u.dateOfBirth)
              from User u
          """)
    Page<UserResponse> findAllUser(Pageable pageable);

    /**
     * Invokes a stored procedure named "REMOVE_ADMIN" to remove admin privileges.
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.username = 'admin'")
    void removeAdmin();
}