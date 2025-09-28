package com.demo.productstore.product.model;

import com.demo.productstore.currency.model.Price;
import org.springframework.util.Assert;

public record ProductCreate(
        ProductCode code, String name, String description, Price priceEur, boolean isAvailable
) {
    public ProductCreate {
        Assert.notNull(code, "code should not be null");
        Assert.notNull(name, "name should not be null");
        Assert.notNull(description, "description should not be null");
        Assert.hasLength(name, "name should not be empty");
        Assert.notNull(priceEur, "priceEur should not be empty");
    }
}
