package com.demo.productstore.product.db;

import com.demo.productstore.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByExternalId(String externalId);

    Optional<ProductEntity> findByCode(String code);

    boolean existsByCode(String code);
}
