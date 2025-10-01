package com.demo.productstore.product.model;

import com.demo.productstore.currency.model.Price;
import org.springframework.util.Assert;

/**
 * Represents the data required to create a new product.
 *
 * @param code        the unique product code
 * @param name        the name of the product
 * @param description the description of the product
 * @param priceEur    the price of the product in EUR
 * @param priceUsd    the price of the product in USD
 * @param isAvailable indicates if the product is available for purchase
 */
public record ProductCreate(
        ProductCode code, String name, String description, Price priceEur, Price priceUsd, boolean isAvailable
) {
    public ProductCreate {
        Assert.notNull(code, "code should not be null");
        Assert.notNull(name, "name should not be null");
        Assert.notNull(description, "description should not be null");
        Assert.hasLength(name, "name should not be empty");
        Assert.notNull(priceEur, "priceEur should not be empty");
        Assert.notNull(priceUsd, "priceUsd should not be empty");
    }
}
