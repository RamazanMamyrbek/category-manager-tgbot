package com.ramazanmamyrbek.categorymanagertgbot.exception;

/**
 * Exception thrown when a requested category is not found in the system.
 * This runtime exception indicates that the category the user is trying to access
 * or manipulate does not exist in the database or data structure.
 */
public class CategoryNotFoundException extends RuntimeException {

    /**
     * Constructs a new CategoryNotFoundException with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public CategoryNotFoundException(String message) {
        super(message);
    }
}

