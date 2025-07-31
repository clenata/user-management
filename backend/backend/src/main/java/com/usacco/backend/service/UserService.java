package com.usacco.backend.service;

import com.usacco.backend.model.User;
import java.util.List;

/**
 * Service interface for user management operations.
 * Provides methods for user registration, authentication, retrieval, updates, and deletion.
 */
public interface UserService {
    
    /**
     * Registers a new user in the system.
     * 
     * @param user The user object containing registration information
     * @return The registered user with generated ID and timestamps
     * @throws IllegalArgumentException if user data is invalid
     * @throws RuntimeException if username or email already exists
     */
    User registerUser(User user);
    
    /**
     * Authenticates a user with the provided username and password.
     * 
     * @param username The username to authenticate
     * @param password The plain text password to verify
     * @return true if authentication is successful, false otherwise
     * @throws IllegalArgumentException if username or password is null or empty
     */
    boolean authenticate(String username, String password);
    
    /**
     * Retrieves all active users from the system.
     * 
     * @return List of all non-deleted users
     */
    List<User> getAllUsers();
    
    /**
     * Retrieves a user by their unique identifier.
     * 
     * @param id The unique identifier of the user
     * @return The user if found
     * @throws com.usacco.backend.exception.UserNotFoundException if user with given ID doesn't exist
     */
    User getUserById(Long id);
    
    /**
     * Updates an existing user's information.
     * 
     * @param id The unique identifier of the user to update
     * @param updatedUser The user object containing updated information
     * @return The updated user object
     * @throws com.usacco.backend.exception.UserNotFoundException if user with given ID doesn't exist
     * @throws IllegalArgumentException if updated user data is invalid
     * @throws RuntimeException if updated username or email conflicts with existing users
     */
    User updateUser(Long id, User updatedUser);
    
    /**
     * Performs a soft delete of a user by setting the isDeleted flag to true.
     * The user record remains in the database but is marked as deleted.
     * 
     * @param id The unique identifier of the user to soft delete
     * @throws com.usacco.backend.exception.UserNotFoundException if user with given ID doesn't exist
     */
    void softDeleteUser(Long id);
}
