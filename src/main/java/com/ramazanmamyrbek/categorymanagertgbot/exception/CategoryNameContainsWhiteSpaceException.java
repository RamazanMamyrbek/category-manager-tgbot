package com.ramazanmamyrbek.categorymanagertgbot.exception;

/**
 * Exception thrown when a category name contains whitespace characters.
 */
public class CategoryNameContainsWhiteSpaceException extends RuntimeException {

    /**
     * Constructs a new CategoryNameContainsWhiteSpaceException with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public CategoryNameContainsWhiteSpaceException(String message) {
        super(message);
    }
}

