package com.demo.productstore.product.service;

import com.demo.productstore.apisupport.mapper.ProductDtoMapper;
import com.demo.productstore.apisupport.model.ProductCreateDto;
import com.demo.productstore.apisupport.model.ProductDto;
import com.demo.productstore.apisupport.model.ProductUpdateDto;
import com.demo.productstore.currency.domain.CurrencyClient;
import com.demo.productstore.currency.model.CurrencyCountryCode;
import com.demo.productstore.currency.model.Price;
import com.demo.productstore.product.domain.ProductRepositoryDomain;
import com.demo.productstore.product.domain.ProductServiceDomain;
import com.demo.productstore.product.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements ProductServiceDomain {

    private static final String FIELD_CODE = "code";
    private static final String USD_COUNTRY_CODE = "USD";

    private final ProductRepositoryDomain repository;
    private final CurrencyClient client;

    @Transactional
    @Override
    public ProductDto createProduct(ProductCreateDto productCreateDto) {
        log.info("Creating new product with code '{}'", productCreateDto.getCode());
        final var priceUsd = convertPriceToUsd(productCreateDto.getPriceEur(), USD_COUNTRY_CODE);
        final var productCreate = ProductDtoMapper.mapProductCreateDtoToProductCreate(productCreateDto, priceUsd);
        return ProductDtoMapper.mapProductToProductDto(repository.persist(productCreate));
    }

    @Override
    public ProductDto getProductByCode(String code) {
        log.info("Fetching product with code '{}'", code);
        return repository.findByCode(code)
                .map(ProductDtoMapper::mapProductToProductDto)
                .orElseThrow(
                        () -> new ProductNotFoundException(generateExceptionMessage(FIELD_CODE, code))
                );
    }

    @Override
    public Collection<ProductDto> getAllProducts(int pageSize, int pageIndex) {
        log.info("Fetching all products, page size: {}, page index: {}", pageSize, pageIndex);
        return repository.findAll(pageSize, pageIndex).stream()
                .map(ProductDtoMapper::mapProductToProductDto)
                .toList();
    }

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

    @Override
    public Price convertPriceToUsd(BigDecimal priceEur, String currencyCode) {
        log.info("Converting price '{}' from currency 'EUR' to '{}'", priceEur, currencyCode);
        var exchangeRate = client.getMidMarketExchangeRate(new CurrencyCountryCode(USD_COUNTRY_CODE));
        return new Price(priceEur).multiplyBy(exchangeRate);
    }

    private static String generateExceptionMessage(String field, String value) {
        return "Product with " + field + ": " + value + " not found";
    }

}
