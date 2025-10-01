package com.demo.productstore.fixture;

import com.demo.productstore.product.db.ProductEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class ProductFixture {

    public static ProductEntity createProductEntity() {
        final Timestamp timestamp = Timestamp.from(Instant.now());
        return new ProductEntity(
                1L,
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                "CODE123456",
                "Product",
                "Test Product",
                BigDecimal.valueOf(9.99).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(11.99).setScale(2, RoundingMode.HALF_UP),
                true,
                timestamp,
                timestamp,
                null
        );
    }
}
