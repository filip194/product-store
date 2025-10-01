package com.demo.productstore.apisupport.mapper;

import com.demo.productstore.apisupport.dto.ProductCreateDto;
import com.demo.productstore.apisupport.dto.ProductDto;
import com.demo.productstore.apisupport.dto.ProductUpdateDto;
import com.demo.productstore.currency.model.Price;
import com.demo.productstore.product.model.Product;
import com.demo.productstore.product.model.ProductCode;
import com.demo.productstore.product.model.ProductCreate;
import com.demo.productstore.product.model.ProductUpdate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductDtoMapperTest {

    @Test
    void testMapProductCreateDtoToProductCreate() {
        final ProductCreateDto dto = new ProductCreateDto();
        dto.setCode("CODE123456");
        dto.setName("Test Product");
        dto.setDescription("Test Description");
        dto.setPriceEur(BigDecimal.valueOf(10.5));
        dto.setAvailable(true);

        final Price priceUsd = new Price(BigDecimal.valueOf(12.0));
        final ProductCreate productCreate = ProductDtoMapper.mapProductCreateDtoToProductCreate(dto, priceUsd);

        assertEquals(dto.getCode(), productCreate.code().value());
        assertEquals(dto.getName(), productCreate.name());
        assertEquals(dto.getDescription(), productCreate.description());
        assertEquals(dto.getPriceEur(), productCreate.priceEur().value());
        assertEquals(priceUsd, productCreate.priceUsd());
        assertEquals(dto.isAvailable(), productCreate.isAvailable());
    }

    @Test
    void testMapProductUpdateDtoToProductUpdate() {
        final ProductUpdateDto dto = new ProductUpdateDto();
        dto.setName("Updated Name");
        dto.setDescription("Updated Description");
        dto.setPriceEur(BigDecimal.valueOf(20.0));
        dto.setAvailable(false);

        final Price priceUsd = new Price(BigDecimal.valueOf(22.0));
        final ProductUpdate productUpdate = ProductDtoMapper.mapProductUpdateDtoToProductUpdate(dto, priceUsd);

        assertEquals(dto.getName(), productUpdate.name());
        assertEquals(dto.getDescription(), productUpdate.description());
        assertEquals(dto.getPriceEur(), productUpdate.priceEur().value());
        assertEquals(priceUsd, productUpdate.priceUsd());
        assertEquals(dto.isAvailable(), productUpdate.isAvailable());
    }

    @Test
    void testMapProductToProductDto() {
        final UUID externalId = UUID.randomUUID();
        final Timestamp timestamp = Timestamp.from(Instant.now());

        final Product product = new Product(
                1L,
                externalId,
                new ProductCode("CODE123456"),
                "Test Product",
                "Test Description",
                new Price(BigDecimal.valueOf(10.5)),
                new Price(BigDecimal.valueOf(12.0)),
                true,
                timestamp,
                timestamp,
                null
        );

        final ProductDto dto = ProductDtoMapper.mapProductToProductDto(product);

        assertEquals(product.externalId(), dto.getExternalId());
        assertEquals(product.code().value(), dto.getCode());
        assertEquals(product.name(), dto.getName());
        assertEquals(product.description(), dto.getDescription());
        assertEquals(product.priceEur().value(), dto.getPriceEur());
        assertEquals(product.priceUsd().value(), dto.getPriceUsd());
        assertEquals(product.isAvailable(), dto.isAvailable());
        assertEquals(product.created(), dto.getCreated());
        assertEquals(product.updated(), dto.getUpdated());
        assertEquals(product.deleted(), dto.getDeleted());
    }
}
