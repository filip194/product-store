package com.demo.productstore.product.db;

import com.demo.productstore.product.domain.ProductRepositoryDomain;
import com.demo.productstore.product.mapper.ProductMapper;
import com.demo.productstore.product.model.Product;
import com.demo.productstore.product.model.ProductCreate;
import com.demo.productstore.product.model.ProductUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductRepository implements ProductRepositoryDomain {

    private final ProductJpaRepository repository;

    @Override
    public Product persist(ProductCreate productCreate) {
        return ProductMapper.mapToProduct(repository.save(ProductMapper.mapToProductEntity(productCreate)));
    }

    @Override
    public Optional<Product> findByExternalId(UUID externalId) {
        return repository.findByExternalId(externalId)
                .filter(productEntity -> Objects.isNull(productEntity.getDeleted()))
                .map(ProductMapper::mapToProduct);
    }

    @Override
    public Optional<Product> findByCode(String code) {
        return repository.findByCode(code)
                .filter(productEntity -> Objects.isNull(productEntity.getDeleted()))
                .map(ProductMapper::mapToProduct);
    }

    @Override
    public Collection<Product> findAll(int pageSize, int pageIndex) {
        return repository.findAll(Pageable.ofSize(pageSize).withPage(pageIndex)).stream()
                .filter(productEntity -> Objects.isNull(productEntity.getDeleted()))
                .map(ProductMapper::mapToProduct)
                .toList();
    }

    @Override
    public Optional<Product> updateByCode(String code, ProductUpdate productUpdate) {
        final var entity = repository
                .findByCode(code)
                .filter(productEntity -> Objects.isNull(productEntity.getDeleted()));

        if (entity.isPresent()) {
            final var updatedEntity = entity.get();
            ProductMapper.mapToProductEntity(updatedEntity, productUpdate);
            updatedEntity.setUpdated(Timestamp.from(Instant.now()));
            return Optional.of(ProductMapper.mapToProduct(repository.save(updatedEntity)));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Product> softDeleteByCode(String code) {
        final var entity = repository
                .findByCode(code)
                .filter(productEntity -> Objects.isNull(productEntity.getDeleted()));

        if (entity.isPresent()) {
            entity.get().setDeleted(Timestamp.from(Instant.now()));
            repository.save(entity.get());
            return Optional.of(ProductMapper.mapToProduct(entity.get()));
        }
        return Optional.empty();
    }

}
