package com.demo.productstore.product.mapper;

import com.demo.productstore.currency.model.Price;
import com.demo.productstore.product.db.ProductEntity;
import com.demo.productstore.product.model.Product;
import com.demo.productstore.product.model.ProductCode;
import com.demo.productstore.product.model.ProductCreate;
import com.demo.productstore.product.model.ProductUpdate;

import java.util.Optional;
import java.util.UUID;

/**
 * Mapper class for converting between ProductEntity and Product domain models.
 */
public class ProductMapper {

    /**
     * Maps a ProductEntity to a Product domain model.
     *
     * @param entity the ProductEntity to map
     * @return the mapped Product domain model
     */
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

    /**
     * Maps a Product domain model to a ProductEntity.
     *
     * @param product the Product domain model to map
     * @return the mapped ProductEntity
     */
    public static ProductEntity mapProductToProductEntity(Product product) {
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

    /**
     * Updates a ProductEntity with values from a ProductUpdate.
     * Only non-null values from ProductUpdate will be applied to the entity.
     *
     * @param entity        the ProductEntity to update
     * @param productUpdate the ProductUpdate containing new values
     */
    public static void mapProductUpdateToProductEntity(ProductEntity entity, ProductUpdate productUpdate) {
        entity.setName(Optional.ofNullable(productUpdate.name()).orElse(entity.getName()));
        entity.setDescription(Optional.ofNullable(productUpdate.description()).orElse(entity.getDescription()));
        entity.setPriceEur(Optional.ofNullable(productUpdate.priceEur()).map(Price::value).orElse(entity.getPriceEur()));
        entity.setPriceUsd(Optional.ofNullable(productUpdate.priceUsd()).map(Price::value).orElse(entity.getPriceUsd()));
        entity.setAvailable(Optional.of(productUpdate.isAvailable()).orElse(entity.isAvailable()));
    }

    /**
     * Maps a ProductCreate to a new ProductEntity.
     *
     * @param productCreate the ProductCreate to map
     * @return the mapped ProductEntity
     */
    public static ProductEntity mapProductCreateToProductEntity(ProductCreate productCreate) {
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
