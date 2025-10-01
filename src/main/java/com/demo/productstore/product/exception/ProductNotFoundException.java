package com.demo.productstore.product.exception;

import com.demo.productstore.apisupport.exception.ResourceNotFoundException;

/**
 * Exception thrown when a product is not found.
 */
public class ProductNotFoundException extends ResourceNotFoundException {

    /**
     * Constructs a new ProductNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
}
