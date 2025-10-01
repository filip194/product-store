package com.demo.productstore.security.role.db;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * JPA repository interface for managing RoleEntity.
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    /**
     * Finds a RoleEntity by its name.
     *
     * @param name the role name
     * @return an Optional containing the found RoleEntity, or empty if not found
     */
    Optional<RoleEntity> findByName(String name);

    /**
     * Finds a RoleEntity by its external ID.
     *
     * @param roleId the external UUID of the role
     * @return an Optional containing the found RoleEntity, or empty if not found
     */
    Optional<RoleEntity> findByExternalId(UUID roleId);

}

