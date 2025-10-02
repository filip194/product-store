package com.demo.productstore.fixture;

import com.demo.productstore.security.role.db.RoleEntity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.UUID;

public class RoleFixture {

    public static RoleEntity createAdminRoleEntity() {
        final Timestamp timestamp = Timestamp.from(Instant.now());
        return new RoleEntity(
                1L,
                UUID.fromString("123e4567-e89b-12d3-a456-426614174001"),
                "ADMIN",
                new HashSet<>(),
                timestamp,
                timestamp
        );
    }

    public static RoleEntity createUserRoleEntity() {
        final Timestamp timestamp = Timestamp.from(Instant.now());
        return new RoleEntity(
                2L,
                UUID.fromString("123e4567-e89b-12d3-a456-426614174002"),
                "USER",
                new HashSet<>(),
                timestamp,
                timestamp
        );
    }

    public static RoleEntity createAuthenticatedUserRoleEntity() {
        final Timestamp timestamp = Timestamp.from(Instant.now());
        return new RoleEntity(
                3L,
                UUID.fromString("123e4567-e89b-12d3-a456-426614174003"),
                "AUTHENTICATED_USER",
                new HashSet<>(),
                timestamp,
                timestamp
        );
    }
}
