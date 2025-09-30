package com.demo.productstore.product.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    boolean existsByCode(String code);

    Optional<ProductEntity> findByCode(String code);

    Optional<ProductEntity> findByExternalId(UUID externalId);

}
