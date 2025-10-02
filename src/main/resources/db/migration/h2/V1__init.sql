CREATE SCHEMA IF NOT EXISTS product;

SET SCHEMA product;

CREATE TABLE IF NOT EXISTS product.product (
    id BIGSERIAL,
    external_id UUID NOT NULL UNIQUE,
    code VARCHAR(10) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price_eur NUMERIC CHECK (price_eur >= 0) NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS product.users (
    id BIGSERIAL,
    external_id UUID NOT NULL UNIQUE,
    type VARCHAR(50) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(60) NOT NULL,
    name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    age INT,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS product.roles (
    id BIGSERIAL,
    external_id UUID NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS product.user_roles (
    user_entity_id BIGINT NOT NULL,
    role_entity_id BIGINT NOT NULL,
    PRIMARY KEY (user_entity_id, role_entity_id)
);

ALTER TABLE IF EXISTS product.user_roles
ADD CONSTRAINT user_entity_id_fk_user_id FOREIGN KEY (user_entity_id) REFERENCES users;

ALTER TABLE IF EXISTS product.user_roles
ADD CONSTRAINT role_entity_id_fk_role_id FOREIGN KEY (role_entity_id) REFERENCES roles;
