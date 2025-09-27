CREATE SEQUENCE pk_product_seq start WITH 4 increment BY 1;
CREATE SEQUENCE pk_roles_seq start WITH 4 increment BY 1;
CREATE SEQUENCE pk_users_seq start WITH 6 increment BY 1;

CREATE TABLE product (
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

CREATE TABLE users (
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

CREATE TABLE roles (
    id BIGINT NOT NULL,
    external_id UUID NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE user_roles (
    user_entity_id BIGINT NOT NULL,
    role_entity_id BIGINT NOT NULL,
    PRIMARY KEY (user_entity_id, role_entity_id)
);

ALTER TABLE IF EXISTS user_roles
ADD CONSTRAINT user_entity_id_fk_user_id FOREIGN KEY (user_entity_id) REFERENCES users;

ALTER TABLE IF EXISTS user_roles
ADD CONSTRAINT role_entity_id_fk_role_id FOREIGN KEY (role_entity_id) REFERENCES roles;
