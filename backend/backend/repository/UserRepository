package com.usacco.backend.repository;

import com.usacco.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity operations.
 * Extends JpaRepository to provide basic CRUD operations and custom query methods.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find a user by their username.
     * 
     * @param username The username to search for
     * @return Optional containing the user if found, empty otherwise
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Find a user by their email address.
     * 
     * @param email The email address to search for
     * @return Optional containing the user if found, empty otherwise
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Find all users that are not marked as deleted.
     * 
     * @return List of all active (non-deleted) users
     */
    List<User> findByIsDeletedFalse();
}
