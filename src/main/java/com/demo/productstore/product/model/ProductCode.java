package com.demo.productstore.product.model;

import org.springframework.util.Assert;

/**
 * Represents a product code with specific validation rules.
 *
 * @param value the product code value
 */
public record ProductCode(String value) {

    private static final int PRODUCT_CODE_LENGTH = 10;

    public ProductCode {
        Assert.notNull(value, "Product code value should not be null");
        if (value.length() != PRODUCT_CODE_LENGTH) {
            throw new IllegalArgumentException(
                    "Invalid product code length expected %s got %s".formatted(PRODUCT_CODE_LENGTH, value.length())
            );
        }
    }
}
