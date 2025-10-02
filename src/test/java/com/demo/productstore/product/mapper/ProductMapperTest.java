package com.demo.productstore.product.mapper;

import com.demo.productstore.currency.model.Price;
import com.demo.productstore.product.db.ProductEntity;
import com.demo.productstore.product.model.Product;
import com.demo.productstore.product.model.ProductCode;
import com.demo.productstore.product.model.ProductCreate;
import com.demo.productstore.product.model.ProductUpdate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    @Test
    void shouldMapProductEntityToProduct() {
        final UUID externalId = UUID.randomUUID();
        final Timestamp timestamp = Timestamp.from(Instant.now());

        final ProductEntity entity = new ProductEntity();
        entity.setId(1L);
        entity.setExternalId(externalId);
        entity.setCode("CODE123456");
        entity.setName("Test Product");
        entity.setDescription("Test Description");
        entity.setPriceEur(BigDecimal.valueOf(10.50));
        entity.setPriceUsd(BigDecimal.valueOf(12.00));
        entity.setAvailable(true);
        entity.setCreated(timestamp);
        entity.setUpdated(timestamp);
        entity.setDeleted(null);

        final Product product = ProductMapper.mapToProduct(entity);

        assertEquals(1L, product.id());
        assertEquals(externalId, product.externalId());
        assertEquals("CODE123456", product.code().value());
        assertEquals("Test Product", product.name());
        assertEquals("Test Description", product.description());
        assertEquals(BigDecimal.valueOf(10.50).setScale(2, RoundingMode.HALF_UP), product.priceEur().value());
        assertEquals(BigDecimal.valueOf(12.00).setScale(2, RoundingMode.HALF_UP), product.priceUsd().value());
        assertTrue(product.isAvailable());
    }

    @Test
    void shouldMapProductToProductEntity() {
        final UUID externalId = UUID.randomUUID();
        final Timestamp timestamp = Timestamp.from(Instant.now());

        final Product product = new Product(
                1L,
                externalId,
                new ProductCode("CODE123456"),
                "Test Product",
                "Test Description",
                new Price(BigDecimal.valueOf(10.50)),
                new Price(BigDecimal.valueOf(12.00)),
                true,
                timestamp,
                timestamp,
                null
        );

        final ProductEntity entity = ProductMapper.mapProductToProductEntity(product);

        assertEquals(externalId, entity.getExternalId());
        assertEquals("CODE123456", entity.getCode());
        assertEquals("Test Product", entity.getName());
        assertEquals("Test Description", entity.getDescription());
        assertEquals(BigDecimal.valueOf(10.50).setScale(2, RoundingMode.HALF_UP), entity.getPriceEur());
        assertEquals(BigDecimal.valueOf(12.00).setScale(2, RoundingMode.HALF_UP), entity.getPriceUsd());
        assertTrue(entity.isAvailable());
    }

    @Test
    void shouldMapProductUpdateToProductEntity() {
        final ProductEntity entity = new ProductEntity();

        entity.setName("Old Name");
        entity.setDescription("Old Description");
        entity.setPriceEur(BigDecimal.valueOf(10.00));
        entity.setPriceUsd(BigDecimal.valueOf(11.00));
        entity.setAvailable(false);

        final ProductUpdate update = new ProductUpdate(
                "New Name",
                "Old Description",
                new Price(BigDecimal.valueOf(20.00)),
                new Price(BigDecimal.valueOf(22.00)),
                true
        );

        ProductMapper.mapProductUpdateToProductEntity(entity, update);

        assertEquals("New Name", entity.getName());
        assertEquals("Old Description", entity.getDescription());
        assertEquals(BigDecimal.valueOf(20.00).setScale(2, RoundingMode.HALF_UP), entity.getPriceEur());
        assertEquals(BigDecimal.valueOf(22.00).setScale(2, RoundingMode.HALF_UP), entity.getPriceUsd());
        assertTrue(entity.isAvailable());
    }

    @Test
    void shouldMapProductCreateToProductEntity() {
        final ProductCreate create = new ProductCreate(
                new ProductCode("CODE123456"),
                "Test Product",
                "Test Description",
                new Price(BigDecimal.valueOf(10.50)),
                new Price(BigDecimal.valueOf(12.00)),
                true
        );

        final ProductEntity entity = ProductMapper.mapProductCreateToProductEntity(create);

        assertNotNull(entity.getExternalId());
        assertEquals("CODE123456", entity.getCode());
        assertEquals("Test Product", entity.getName());
        assertEquals("Test Description", entity.getDescription());
        assertEquals(BigDecimal.valueOf(10.50).setScale(2, RoundingMode.HALF_UP), entity.getPriceEur());
        assertEquals(BigDecimal.valueOf(12.00).setScale(2, RoundingMode.HALF_UP), entity.getPriceUsd());
        assertTrue(entity.isAvailable());
    }
}
