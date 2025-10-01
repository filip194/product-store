package com.demo.productstore.product.service;

import com.demo.productstore.apisupport.dto.ProductCreateDto;
import com.demo.productstore.apisupport.dto.ProductDto;
import com.demo.productstore.apisupport.dto.ProductUpdateDto;
import com.demo.productstore.apisupport.mapper.ProductDtoMapper;
import com.demo.productstore.currency.domain.CurrencyClient;
import com.demo.productstore.currency.model.CurrencyCountryCode;
import com.demo.productstore.currency.model.Price;
import com.demo.productstore.product.domain.ProductRepositoryDomain;
import com.demo.productstore.product.domain.ProductServiceDomain;
import com.demo.productstore.product.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Service implementation for managing products in the system.
 * <p>
 * This class provides methods for creating, retrieving, updating, and deleting products.
 * It also handles price conversion to USD using an external currency service.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements ProductServiceDomain {

    private static final String FIELD_CODE = "code";
    private static final String USD_COUNTRY_CODE = "USD";

    private final ProductRepositoryDomain repository;
    private final CurrencyClient client;

    /**
     * Creates a new product.
     *
     * @param productCreateDto the product creation DTO
     * @return the created Product DTO
     */
    @Transactional
    @Override
    public ProductDto createProduct(ProductCreateDto productCreateDto) {
        log.info("Creating new product with code '{}'", productCreateDto.getCode());
        final var priceUsd = convertPriceToUsd(productCreateDto.getPriceEur(), USD_COUNTRY_CODE);
        final var productCreate = ProductDtoMapper.mapProductCreateDtoToProductCreate(productCreateDto, priceUsd);
        return ProductDtoMapper.mapProductToProductDto(repository.persist(productCreate));
    }

    /**
     * Retrieves a product by its code.
     *
     * @param code the product code
     * @return the Product DTO
     * @throws ProductNotFoundException if the product with the specified code is not found
     */
    @Override
    public ProductDto getProductByCode(String code) {
        log.info("Fetching product with code '{}'", code);
        return repository.findByCode(code)
                .map(ProductDtoMapper::mapProductToProductDto)
                .orElseThrow(
                        () -> new ProductNotFoundException(generateExceptionMessage(FIELD_CODE, code))
                );
    }

    /**
     * Retrieves all products with pagination.
     *
     * @param pageable the pagination information
     * @return a collection of Product DTOs
     */
    @Override
    public Collection<ProductDto> getAllProducts(Pageable pageable) {
        log.info("Fetching all products, page size: {}, page index: {}", pageable.getPageSize(), pageable.getPageNumber());
        return repository.findAll(pageable).stream()
                .map(ProductDtoMapper::mapProductToProductDto)
                .toList();
    }

    /**
     * Updates a Product by its code with the provided update details.
     *
     * @param code             the product code
     * @param productUpdateDto the product update DTO
     * @return the updated Product DTO
     * @throws ProductNotFoundException if the product with the specified code is not found
     */
    @Transactional
    @Override
    public ProductDto updateProductByCode(String code, ProductUpdateDto productUpdateDto) {
        log.info("Fetching product with code '{}' for update", code);
        return repository.findByCode(code)
                .map(product -> {
                    log.info("Updating product with code '{}'", code);
                    final var priceUsd = convertPriceToUsd(productUpdateDto.getPriceEur(), USD_COUNTRY_CODE);
                    var productUpdate = ProductDtoMapper.mapProductUpdateDtoToProductUpdate(productUpdateDto, priceUsd);
                    return repository.updateByCode(code, productUpdate)
                            .map(ProductDtoMapper::mapProductToProductDto)
                            .orElseThrow(() -> new ProductNotFoundException(generateExceptionMessage(FIELD_CODE, code)));
                })
                .orElseThrow(() -> {
                    log.error("Product with code '{}' not found during update", code);
                    return new ProductNotFoundException(generateExceptionMessage(FIELD_CODE, code));
                });
    }

    /**
     * Deletes a Product by its code.
     *
     * @param code the product code
     * @return the deleted Product DTO
     * @throws ProductNotFoundException if the product with the specified code is not found
     */
    @Transactional
    @Override
    public ProductDto deleteProductByCode(String code) {
        log.info("Deleting product with code '{}'", code);
        return repository.softDeleteByCode(code)
                .map(ProductDtoMapper::mapProductToProductDto)
                .orElseThrow(
                        () -> new ProductNotFoundException(generateExceptionMessage(FIELD_CODE, code))
                );
    }

    /**
     * Converts a given price in EUR to USD based on the specified currency code.
     *
     * @param priceEur     the price in EUR to convert
     * @param currencyCode the target currency code (e.g., "USD")
     * @return the converted Price in USD
     */
    @Override
    public Price convertPriceToUsd(BigDecimal priceEur, String currencyCode) {
        log.info("Converting price '{}' from currency 'EUR' to '{}'", priceEur, currencyCode);
        var exchangeRate = client.getMidMarketExchangeRate(new CurrencyCountryCode(USD_COUNTRY_CODE));
        return new Price(priceEur).multiplyBy(exchangeRate);
    }

    /**
     * Generates a standardized exception message for not found products.
     *
     * @param field the field used for lookup (e.g., "code")
     * @param value the value of the field
     * @return the generated exception message
     */
    private static String generateExceptionMessage(String field, String value) {
        return "Product with " + field + ": " + value + " not found";
    }

}
