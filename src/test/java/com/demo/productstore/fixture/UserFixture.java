package com.demo.productstore.fixture;

import com.demo.productstore.security.UserType;
import com.demo.productstore.security.user.db.UserEntity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.UUID;

public class UserFixture {

    public static UserEntity createUserEntity() {
        final Timestamp timestamp = Timestamp.from(Instant.now());
        return new UserEntity(
                1L,
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                UserType.USER,
                "username",
                "password",
                "name",
                "last_name",
                "email@test.com",
                25,
                timestamp,
                timestamp,
                null,
                new HashSet<>()
        );
    }
}
