package com.demo.productstore.product.domain;

import com.demo.productstore.apisupport.dto.ProductCreateDto;
import com.demo.productstore.apisupport.dto.ProductDto;
import com.demo.productstore.apisupport.dto.ProductUpdateDto;
import com.demo.productstore.currency.model.Price;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Service interface for managing products in the domain layer.
 */
public interface ProductServiceDomain {

    /**
     * Creates a new product.
     *
     * @param productCreateDto the product creation DTO
     * @return the created Product DTO
     */
    ProductDto createProduct(ProductCreateDto productCreateDto);

    /**
     * Retrieves a product by its code.
     *
     * @param code the product code
     * @return the Product DTO
     */
    ProductDto getProductByCode(String code);

    /**
     * Retrieves all products with pagination.
     *
     * @param size the number of products per page
     * @param page the page index (0-based)
     * @return a collection of Product DTOs
     */
    Collection<ProductDto> getAllProducts(int size, int page);

    /**
     * Updates a Product by its code with the provided update details.
     *
     * @param code             the product code
     * @param productUpdateDto the product update DTO
     * @return the updated Product DTO
     */
    ProductDto updateProductByCode(String code, ProductUpdateDto productUpdateDto);

    /**
     * Deletes a Product by its code.
     *
     * @param code the product code
     * @return the deleted Product DTO
     */
    ProductDto deleteProductByCode(String code);

    /**
     * Converts a given price to USD based on the specified currency code.
     *
     * @param price        the price to convert
     * @param currencyCode the currency code of the price
     * @return the converted Price in USD
     */
    Price convertPriceToUsd(BigDecimal price, String currencyCode);

}
