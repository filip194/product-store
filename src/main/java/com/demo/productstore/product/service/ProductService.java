package com.demo.productstore.product.service;

import com.demo.productstore.apisupport.mapper.ProductDtoMapper;
import com.demo.productstore.apisupport.model.ProductCreateDto;
import com.demo.productstore.apisupport.model.ProductDto;
import com.demo.productstore.apisupport.model.ProductUpdateDto;
import com.demo.productstore.currency.model.Price;
import com.demo.productstore.product.db.ProductRepository;
import com.demo.productstore.product.domain.ProductServiceDomain;
import com.demo.productstore.product.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements ProductServiceDomain {

    private static final String FIELD_EXTERNAL_ID = "externalId";
    private static final String FIELD_CODE = "code";

    private final ProductRepository repository;

    @Transactional
    @Override
    public ProductDto createProduct(ProductCreateDto productCreateDto) {
        final var productCreate = ProductDtoMapper.mapProductCreateDtoToProductCreate(productCreateDto);
        return ProductDtoMapper.mapProductToProductDto(repository.persist(productCreate));
    }

    @Override
    public ProductDto getProductByExternalId(UUID externalId) {
        return repository.findByExternalId(externalId)
                .map(ProductDtoMapper::mapProductToProductDto)
                .orElseThrow(
                        () -> new ProductNotFoundException(generateExceptionMessage(FIELD_EXTERNAL_ID, externalId.toString()))
                );
    }

    @Override
    public ProductDto getProductByCode(String code) {
        return repository.findByCode(code)
                .map(ProductDtoMapper::mapProductToProductDto)
                .orElseThrow(
                        () -> new ProductNotFoundException(generateExceptionMessage(FIELD_CODE, code))
                );
    }

    @Override
    public Collection<ProductDto> getAllProducts(int pageSize, int pageIndex) {
        return repository.findAll(pageSize, pageIndex).stream()
                .map(ProductDtoMapper::mapProductToProductDto)
                .toList();
    }

    @Transactional
    @Override
    public ProductDto updateProductByCode(String code, ProductUpdateDto productUpdateDto) {
        // TODO fetch real USD price from HNB client
        final Price priceUsd = new Price(new BigDecimal("0.0"));
        final var productUpdate = ProductDtoMapper.mapProductUpdateDtoToProductUpdate(productUpdateDto, priceUsd);
        return repository.updateByCode(code, productUpdate)
                .map(ProductDtoMapper::mapProductToProductDto)
                .orElseThrow(
                        () -> new ProductNotFoundException(generateExceptionMessage(FIELD_CODE, code))
                );
    }

    @Transactional
    @Override
    public ProductDto deleteProductByCode(String code) {
        return repository.softDeleteByCode(code)
                .map(ProductDtoMapper::mapProductToProductDto)
                .orElseThrow(
                        () -> new ProductNotFoundException(generateExceptionMessage(FIELD_CODE, code))
                );
    }

    private static String generateExceptionMessage(String field, String value) {
        return "Product with " + field + ": " + value + " not found";
    }

}
