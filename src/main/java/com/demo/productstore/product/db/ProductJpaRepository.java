package com.demo.productstore.product.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * JPA repository interface for managing ProductEntity.
 */
@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    /**
     * Finds a ProductEntity by its code.
     *
     * @param code the product code
     * @return an Optional containing the found ProductEntity, or empty if not found
     */
    Optional<ProductEntity> findByCode(String code);

    /**
     * Finds a ProductEntity by its external ID.
     *
     * @param externalId the external UUID of the product
     * @return an Optional containing the found ProductEntity, or empty if not found
     */
    Optional<ProductEntity> findByExternalId(UUID externalId);

}
