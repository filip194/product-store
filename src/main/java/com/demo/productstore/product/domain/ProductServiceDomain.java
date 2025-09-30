package com.demo.productstore.product.domain;

import com.demo.productstore.apisupport.model.ProductCreateDto;
import com.demo.productstore.apisupport.model.ProductDto;
import com.demo.productstore.apisupport.model.ProductUpdateDto;
import com.demo.productstore.currency.model.Price;

import java.math.BigDecimal;
import java.util.Collection;

public interface ProductServiceDomain {

    ProductDto createProduct(ProductCreateDto productCreateDto);

    ProductDto getProductByCode(String code);

    Collection<ProductDto> getAllProducts(int size, int page);

    ProductDto updateProductByCode(String code, ProductUpdateDto productUpdateDto);

    ProductDto deleteProductByCode(String code);

    Price convertPriceToUsd(BigDecimal price, String currencyCode);

}
