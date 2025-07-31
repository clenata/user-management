package com.usacco.backend.controller;

import com.usacco.backend.model.User;
import com.usacco.backend.service.UserService;
import com.usacco.backend.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST controller for user management operations.
 * Provides endpoints for user registration, authentication, CRUD operations, and soft deletion.
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Register a new user.
     * 
     * @param user The user object containing registration information
     * @return ResponseEntity with the created user and HTTP 201 status
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        logger.info("Registration attempt for user: {}", user);
        try {
            User createdUser = userService.registerUser(user);
            logger.info("Registration successful for username: {}", user.getUsername());
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.warn("Registration failed (bad request) for username: {}: {}", user.getUsername(), e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            logger.warn("Registration failed (conflict) for username: {}: {}", user.getUsername(), e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during registration for username: {}: {}", user.getUsername(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Authenticate a user with username and password.
     * 
     * @param loginRequest Object containing username and password
     * @return ResponseEntity with authentication result
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            boolean isAuthenticated = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            if (isAuthenticated) {
                return ResponseEntity.ok().body("Authentication successful");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid input parameters");
        }
    }

    /**
     * Get all active users (non-deleted).
     * 
     * @return ResponseEntity with list of all active users
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Get a user by their ID.
     * 
     * @param id The user ID
     * @return ResponseEntity with the user if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid user ID");
        }
    }

    /**
     * Update an existing user.
     * 
     * @param id The user ID to update
     * @param userDetails The updated user information
     * @return ResponseEntity with the updated user
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid input data");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username or email already exists");
        }
    }

    /**
     * Soft delete a user by setting isDeleted flag to true.
     * 
     * @param id The user ID to soft delete
     * @return ResponseEntity with deletion confirmation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> softDeleteUser(@PathVariable Long id) {
        try {
            userService.softDeleteUser(id);
            return ResponseEntity.ok().body("User soft deleted successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid user ID");
        }
    }

    /**
     * Inner class for login request containing username and password.
     */
    public static class LoginRequest {
        private String username;
        private String password;

        // Default constructor
        public LoginRequest() {}

        // Constructor with parameters
        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        // Getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
