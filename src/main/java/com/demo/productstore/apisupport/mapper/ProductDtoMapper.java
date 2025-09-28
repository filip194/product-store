package com.demo.productstore.apisupport.mapper;

import com.demo.productstore.apisupport.model.ProductCreateDto;
import com.demo.productstore.apisupport.model.ProductDto;
import com.demo.productstore.apisupport.model.ProductUpdateDto;
import com.demo.productstore.currency.model.Price;
import com.demo.productstore.product.model.Product;
import com.demo.productstore.product.model.ProductCode;
import com.demo.productstore.product.model.ProductCreate;
import com.demo.productstore.product.model.ProductUpdate;

public class ProductDtoMapper {

    public static ProductCreate mapProductCreateDtoToProductCreate(ProductCreateDto productCreateDto) {
        return new ProductCreate(
                new ProductCode(productCreateDto.getCode()),
                productCreateDto.getName(),
                productCreateDto.getDescription(),
                new Price(productCreateDto.getPriceEur()),
                productCreateDto.isAvailable()
        );
    }

    public static ProductUpdate mapProductUpdateDtoToProductUpdate(ProductUpdateDto productUpdateDto, Price priceUsd) {
        return new ProductUpdate(
                productUpdateDto.getName(),
                productUpdateDto.getDescription(),
                new Price(productUpdateDto.getPriceEur()),
                priceUsd,
                productUpdateDto.isAvailable()
        );
    }

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
