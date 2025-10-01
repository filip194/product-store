package com.demo.productstore.apisupport.mapper;

import com.demo.productstore.apisupport.dto.ProductCreateDto;
import com.demo.productstore.apisupport.dto.ProductDto;
import com.demo.productstore.apisupport.dto.ProductUpdateDto;
import com.demo.productstore.currency.model.Price;
import com.demo.productstore.product.model.Product;
import com.demo.productstore.product.model.ProductCode;
import com.demo.productstore.product.model.ProductCreate;
import com.demo.productstore.product.model.ProductUpdate;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * Mapper class for converting between Product DTOs and domain models.
 */
public class ProductDtoMapper {

    /**
     * Maps ProductCreateDto to ProductCreate domain model.
     *
     * @param productCreateDto the product creation DTO
     * @param priceUsd         the price in USD
     * @return the ProductCreate domain model
     */
    public static ProductCreate mapProductCreateDtoToProductCreate(ProductCreateDto productCreateDto, Price priceUsd) {
        final var timestamp = Timestamp.from(Instant.now());
        return new ProductCreate(
                new ProductCode(productCreateDto.getCode()),
                productCreateDto.getName(),
                productCreateDto.getDescription(),
                new Price(productCreateDto.getPriceEur()),
                priceUsd,
                productCreateDto.isAvailable()
        );
    }

    /**
     * Maps ProductUpdateDto to ProductUpdate domain model.
     *
     * @param productUpdateDto the product update DTO
     * @param priceUsd         the price in USD
     * @return the ProductUpdate domain model
     */
    public static ProductUpdate mapProductUpdateDtoToProductUpdate(ProductUpdateDto productUpdateDto, Price priceUsd) {
        return new ProductUpdate(
                productUpdateDto.getName(),
                productUpdateDto.getDescription(),
                new Price(productUpdateDto.getPriceEur()),
                priceUsd,
                productUpdateDto.isAvailable()
        );
    }

    /**
     * Maps Product domain model to ProductDto.
     *
     * @param product the Product domain model
     * @return the ProductDto
     */
    public static ProductDto mapProductToProductDto(Product product) {
        return new ProductDto(
                product.externalId(),
                product.code().value(),
                product.name(),
                product.description(),
                product.priceEur().value(),
                product.priceUsd().value(),
                product.isAvailable(),
                product.created(),
                product.updated(),
                product.deleted()
        );
    }
}
