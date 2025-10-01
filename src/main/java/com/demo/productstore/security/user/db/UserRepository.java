package com.demo.productstore.security.user.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * JPA repository interface for managing UserEntity.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Finds a UserEntity by its username.
     *
     * @param username the username of the user
     * @return an Optional containing the found UserEntity, or empty if not found
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Finds a UserEntity by its external ID.
     *
     * @param userId the external UUID of the user
     * @return an Optional containing the found UserEntity, or empty if not found
     */
    Optional<UserEntity> findByExternalId(UUID userId);

}

