package com.demo.productstore.apisupport.mapper;

import com.demo.productstore.apisupport.dto.ProductCreateDto;
import com.demo.productstore.apisupport.dto.ProductDto;
import com.demo.productstore.apisupport.dto.ProductUpdateDto;
import com.demo.productstore.currency.model.Price;
import com.demo.productstore.product.model.Product;
import com.demo.productstore.product.model.ProductCode;
import com.demo.productstore.product.model.ProductCreate;
import com.demo.productstore.product.model.ProductUpdate;

import java.math.BigDecimal;

/**
 * Mapper class for converting between Product DTOs and domain models.
 */
public class ProductDtoMapper {

    /**
     * Maps ProductCreateDto to ProductCreate domain model.
     *
     * @param productCreateDto the product creation DTO
     * @return the ProductCreate domain model
     */
    public static ProductCreate mapProductCreateDtoToProductCreate(ProductCreateDto productCreateDto) {
        return new ProductCreate(
                new ProductCode(productCreateDto.getCode()),
                productCreateDto.getName(),
                productCreateDto.getDescription(),
                new Price(productCreateDto.getPriceEur()),
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
    public static ProductDto mapProductToProductDto(Product product, Price priceUsd) {
        return new ProductDto(
                product.externalId(),
                product.code().value(),
                product.name(),
                product.description(),
                product.priceEur().value(),
                priceUsd.value(),
                product.isAvailable(),
                product.created(),
                product.updated()
        );
    }
}
