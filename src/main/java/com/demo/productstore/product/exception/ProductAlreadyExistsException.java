package com.demo.productstore.product.exception;

/**
 * Exception thrown when a product already exists.
 */
public class ProductAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new RuntimeFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
