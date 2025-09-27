CREATE SCHEMA IF NOT EXISTS product;

SET search_path TO product,public;

CREATE SEQUENCE IF NOT EXISTS product.pk_product_seq start WITH 4 increment BY 1;
CREATE SEQUENCE IF NOT EXISTS product.pk_roles_seq start WITH 4 increment BY 1;
CREATE SEQUENCE IF NOT EXISTS product.pk_users_seq start WITH 6 increment BY 1;

CREATE TABLE IF NOT EXISTS product.product (
    id BIGINT NOT NULL,
    external_id UUID NOT NULL UNIQUE,
    code VARCHAR(10) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price_eur NUMERIC CHECK (price_eur >= 0) NOT NULL,
    price_usd NUMERIC CHECK (price_usd >= 0) NOT NULL, -- calculate WITH help of HNB API
    is_available BOOLEAN DEFAULT TRUE,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS product.users (
    id BIGINT NOT NULL,
    external_id UUID NOT NULL UNIQUE,
    type VARCHAR(50) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(60) NOT NULL,
    name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    age INT,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS product.roles (
    id BIGINT NOT NULL,
    external_id UUID NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS product.user_roles (
    user_entity_id BIGINT NOT NULL,
    role_entity_id BIGINT NOT NULL,
    PRIMARY KEY (user_entity_id, role_entity_id)
);

DO $$
BEGIN

  BEGIN
    ALTER TABLE IF EXISTS product.user_roles ADD CONSTRAINT user_entity_id_fk_user_id FOREIGN KEY (user_entity_id) REFERENCES users;
  EXCEPTION
    WHEN duplicate_table THEN  -- postgres raises duplicate_table at surprising times. Ex.: for UNIQUE constraints.
    WHEN duplicate_object THEN
      RAISE NOTICE 'Table constraint user_entity_id_fk_user_id already exists';
  END;

END $$;

DO $$
BEGIN

  BEGIN
    ALTER TABLE IF EXISTS product.user_roles ADD CONSTRAINT role_entity_id_fk_role_id FOREIGN KEY (role_entity_id) REFERENCES roles;
  EXCEPTION
    WHEN duplicate_table THEN  -- postgres raises duplicate_table at surprising times. Ex.: for UNIQUE constraints.
    WHEN duplicate_object THEN
      RAISE NOTICE 'Table constraint role_entity_id_fk_role_id already exists';
  END;

END $$;
