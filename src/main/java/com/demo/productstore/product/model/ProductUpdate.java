package com.demo.productstore.product.model;

import com.demo.productstore.currency.model.Price;
import org.springframework.util.Assert;

/**
 * Represents an update to a product's details.
 *
 * @param name        the name of the product
 * @param description the description of the product
 * @param priceEur    the price of the product in EUR
 * @param priceUsd    the price of the product in USD
 * @param isAvailable the availability status of the product
 */
public record ProductUpdate(
        String name, String description, Price priceEur, Price priceUsd, boolean isAvailable
) {
    public ProductUpdate {
        Assert.hasText(name, "Name must not be empty");
        Assert.hasText(description, "Description must not be empty");
        Assert.notNull(priceEur, "Price in EUR must not be null");
        Assert.notNull(priceUsd, "Price in USD must not be null");
    }
}
