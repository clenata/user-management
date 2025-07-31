package com.usacco.backend.exception;

/**
 * Exception thrown when a user is not found in the system.
 * This exception is used when attempting to retrieve, update, or delete a user
 * that doesn't exist or has been deleted.
 */
public class UserNotFoundException extends RuntimeException {
    
    /**
     * Constructs a new UserNotFoundException with the specified detail message.
     * 
     * @param message the detail message
     */
    public UserNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new UserNotFoundException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
