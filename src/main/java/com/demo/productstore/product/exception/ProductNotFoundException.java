package com.demo.productstore.product.exception;

import com.demo.productstore.apisupport.exception.ResourceNotFoundException;

public class ProductNotFoundException extends ResourceNotFoundException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}
