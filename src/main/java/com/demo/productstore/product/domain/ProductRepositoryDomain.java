package com.demo.productstore.product.domain;

import com.demo.productstore.product.model.Product;
import com.demo.productstore.product.model.ProductCreate;
import com.demo.productstore.product.model.ProductUpdate;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepositoryDomain {

    Product persist(ProductCreate productCreate);

    Optional<Product> findByExternalId(UUID externalId);

    Optional<Product> findByCode(String code);

    Collection<Product> findAll(int pageSize, int pageIndex);

    Optional<Product> updateByCode(String code, ProductUpdate productUpdate);

    Optional<Product> softDeleteByCode(String code);

}
