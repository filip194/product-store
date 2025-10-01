package com.demo.productstore.fixture;

import com.demo.productstore.security.role.db.RoleEntity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.UUID;

public class RoleFixture {

    public static RoleEntity createRoleEntity() {
        final Timestamp timestamp = Timestamp.from(Instant.now());
        return new RoleEntity(
                1L,
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                "ADMIN",
                new HashSet<>(),
                timestamp,
                timestamp,
                null
        );
    }
}
