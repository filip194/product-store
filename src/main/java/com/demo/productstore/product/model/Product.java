package com.demo.productstore.product.model;

import com.demo.productstore.currency.model.Price;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Represents a product with various attributes.
 *
 * @param id          the unique identifier of the product
 * @param externalId  the external UUID of the product
 * @param code        the product code
 * @param name        the name of the product
 * @param description the description of the product
 * @param priceEur    the price of the product in EUR
 * @param isAvailable indicates if the product is available
 * @param created     the timestamp when the product was created
 * @param updated     the timestamp when the product was last updated
 */
public record Product(
        Long id, UUID externalId, ProductCode code, String name, String description, Price priceEur,
        boolean isAvailable, Timestamp created, Timestamp updated
) {

    public Product(ProductCode code, String name, String description, Price priceEur,
                   boolean isAvailable, Timestamp created, Timestamp updated) {
        this(null, UUID.randomUUID(), code, name, description, priceEur,
                isAvailable, created, updated);
    }

    public Product {
        Assert.notNull(externalId, "externalId should not be null");
        Assert.notNull(code, "code should not be null");
        Assert.notNull(name, "name should not be null");
        Assert.notNull(description, "description should not be null");
        Assert.hasLength(name, "name should not be empty");
        Assert.notNull(priceEur, "priceEur should not be empty");
    }
}
