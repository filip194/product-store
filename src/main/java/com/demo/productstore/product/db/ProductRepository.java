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
        log.info("Persisting new product with code '{}'", productCreate.code());
        return ProductMapper.mapToProduct(repository.save(ProductMapper.mapProductCreateToProductEntity(productCreate)));
    }

    /**
     * Finds a Product by its code.
     *
     * @param code the product code
     * @return an Optional containing the found Product, or empty if not found
     */
    @Override
    public Optional<Product> findByCode(String code) {
        log.info("Finding product by code '{}'", code);
        return repository.findByCode(code)
                .map(ProductMapper::mapToProduct);
    }

    /**
     * Finds all Products with pagination.
     *
     * @param pageable the pagination information
     * @return a collection of Products for the specified page
     */
    @Override
    public Collection<Product> findAll(Pageable pageable) {
        log.info("Finding all products - page size: {}, page index: {}", pageable.getPageSize(), pageable.getPageNumber());
        return repository.findAll(pageable).stream()
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
        log.info("Updating product with code '{}'", code);
        final var entity = repository.findByCode(code);

        if (entity.isPresent()) {
            final var updatedEntity = entity.get();
            ProductMapper.mapProductUpdateToProductEntity(updatedEntity, productUpdate);
            updatedEntity.setUpdated(Timestamp.from(Instant.now()));
            log.info("Product with code '{}' updated", code);
            return Optional.of(ProductMapper.mapToProduct(repository.save(updatedEntity)));
        }
        log.warn("Product with code '{}' not found for update", code);
        return Optional.empty();
    }

    /**
     * Soft deletes a Product by its code by setting the deleted timestamp.
     *
     * @param code the product code
     * @return an Optional containing the soft-deleted Product, or empty if not found
     */
    @Override
    public boolean deleteByCode(String code) {
        log.info("Soft deleting product with code '{}'", code);
        try {
            repository.deleteByCode(code);
            log.info("Product with code '{}' soft deleted", code);
            return true;
        } catch (Exception e) {
            log.warn("Product with code '{}' not found for deletion", code);
        }

        return false;
    }

}
