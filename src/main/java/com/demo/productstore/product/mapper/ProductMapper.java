package com.demo.productstore.product.mapper;

import com.demo.productstore.currency.model.Price;
import com.demo.productstore.product.db.ProductEntity;
import com.demo.productstore.product.model.Product;
import com.demo.productstore.product.model.ProductCode;
import com.demo.productstore.product.model.ProductCreate;
import com.demo.productstore.product.model.ProductUpdate;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class ProductMapper {

    public static Product mapToProduct(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getExternalId(),
                new ProductCode(entity.getCode()),
                entity.getName(),
                entity.getDescription(),
                new Price(entity.getPriceEur()),
                new Price(entity.getPriceUsd()),
                entity.isAvailable(),
                entity.getCreated(),
                entity.getUpdated(),
                entity.getDeleted()
        );
    }

    public static ProductEntity mapToProductEntity(Product product) {
        return new ProductEntity(
                product.externalId(),
                new ProductCode(product.code().value()),
                product.name(),
                product.description(),
                product.priceEur(),
                product.priceUsd(),
                product.isAvailable()
        );
    }

    public static void mapToProductEntity(ProductEntity entity, ProductUpdate productUpdate) {
        entity.setName(Optional.ofNullable(productUpdate.name()).orElse(entity.getName()));
        entity.setDescription(Optional.ofNullable(productUpdate.description()).orElse(entity.getDescription()));
        entity.setPriceEur(Optional.ofNullable(productUpdate.priceEur()).map(Price::value).orElse(entity.getPriceEur()));
        entity.setPriceUsd(Optional.ofNullable(productUpdate.priceUsd()).map(Price::value).orElse(entity.getPriceUsd()));
        entity.setAvailable(Optional.of(productUpdate.isAvailable()).orElse(entity.isAvailable()));
    }

    public static ProductEntity mapToProductEntity(ProductCreate productCreate) {
        return new ProductEntity(
                UUID.randomUUID(),
                productCreate.code(),
                productCreate.name(),
                productCreate.description(),
                productCreate.priceEur(),
                productCreate.priceUsd(),
                productCreate.isAvailable()
        );
    }
}
