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

/**
 * Repository implementation for managing Product entities.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ProductRepository implements ProductRepositoryDomain {

    private final ProductJpaRepository repository;

    /**
     * Persists a new product in the database.
     *
     * @param productCreate the product creation details
     * @return the persisted Product
     */
    @Override
    public Product persist(ProductCreate productCreate) {
        return ProductMapper.mapToProduct(repository.save(ProductMapper.mapToProductEntity(productCreate)));
    }

    /**
     * Finds a Product by its code.
     *
     * @param code the product code
     * @return an Optional containing the found Product, or empty if not found
     */
    @Override
    public Optional<Product> findByCode(String code) {
        return repository.findByCode(code)
                .filter(productEntity -> Objects.isNull(productEntity.getDeleted()))
                .map(ProductMapper::mapToProduct);
    }

    /**
     * Finds all Products with pagination.
     *
     * @param pageSize  the number of products per page
     * @param pageIndex the page index (0-based)
     * @return a collection of Products for the specified page
     */
    @Override
    public Collection<Product> findAll(int pageSize, int pageIndex) { // TODO pagination object
        return repository.findAll(Pageable.ofSize(pageSize).withPage(pageIndex)).stream()
                .filter(productEntity -> Objects.isNull(productEntity.getDeleted()))
                .map(ProductMapper::mapToProduct)
                .toList();
    }

    /**
     * Updates a Product by its code.
     *
     * @param code          the product code
     * @param productUpdate the product update details
     * @return an Optional containing the updated Product, or empty if not found
     */
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

    /**
     * Soft deletes a Product by its code by setting the deleted timestamp.
     *
     * @param code the product code
     * @return an Optional containing the soft-deleted Product, or empty if not found
     */
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
