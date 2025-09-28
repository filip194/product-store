package com.demo.productstore.product.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByExternalId(UUID externalId);

    Optional<ProductEntity> findByCode(String code);

    boolean existsByCode(String code);
}
