--------------
-- PRODUCTS --
--------------

INSERT INTO product (id, external_id, code, name, description, price_eur, price_usd, is_available, created, updated)
    VALUES
    (
        1,
        '00000000-0000-0000-0000-000000000111',
        'LvSGcjiJEy',
        '34" Monitor',
        'A high-resolution 34-inch monitor with vibrant colors and wide viewing angles.',
        349.99,
        410.85,
        true,
        '2025-09-28 14:10:19.805',
        '2025-09-28 14:10:19.805'
    );

INSERT INTO product (id, external_id, code, name, description, price_eur, price_usd, is_available, created, updated)
    VALUES
    (
        2,
        '00000000-0000-0000-0000-000000000112',
        'FUWTRPaT8L',
        'Gaming Laptop',
        'A powerful gaming laptop with the latest graphics card and high refresh rate display.',
        1299.99,
        1526.06,
        false,
        '2025-09-28 14:10:19.805',
        '2025-09-28 14:10:19.805'
    );

INSERT INTO product (id, external_id, code, name, description, price_eur, price_usd, is_available, created, updated)
    VALUES
    (
        3,
        '00000000-0000-0000-0000-000000000113',
        '2ADhZjimcS',
        'Gaming Mouse',
        'An ergonomic gaming mouse with customizable buttons and RGB lighting.',
        79.99,
        93.90,
        true,
        '2025-09-28 14:10:19.805',
        '2025-09-28 14:10:19.805'
    );

-----------
-- ROLES --
-----------

INSERT INTO roles (id, external_id, name)
    VALUES (1, '00000000-0000-0000-0000-000000000011', 'ADMIN');

INSERT INTO roles (id, external_id, name)
    VALUES (2, '00000000-0000-0000-0000-000000000012', 'USER');

-----------
-- USERS --
-----------

-- passwords for all test users are 123 encrypted with Bcrypt --
INSERT INTO users (id, external_id, username, password, type, email, name, last_name, age, created, updated)
    VALUES
    (
        1,
        '00000000-0000-0000-0000-000000000001',
        'admin',
        '$2y$12$Q.23eJ7aPlJyviQVVX9WpeSYJ1Nkp7UnqYz30J6CVezbFFUbhLadm',
        'ADMIN',
        'admin@admin.net',
        'admin_first_name',
        'admin_last_name',
        '100',
        '2025-09-28 14:10:19.805',
        '2025-09-28 14:10:19.805'
    );

INSERT INTO users (id, external_id, username, password, type, email, name, last_name, age, created, updated)
    VALUES
    (
        2,
        '00000000-0000-0000-0000-000000000002',
        'test_user',
        '$2y$12$vnPcJmY26PobD8Lsgc7/iOz0Sx.A4z/ySLSNM2/Hvb1mv0nDWfk0q',
        'USER',
        'user@user.net',
        'user_first_name',
        'user_last_name',
        '30',
        '2025-09-28 14:10:19.805',
        '2025-09-28 14:10:19.805'
    );

----------------
-- USER_ROLES --
----------------

-- admin --
INSERT INTO user_roles (user_entity_id, role_entity_id) VALUES (1, 1);

-- users --
INSERT INTO user_roles (user_entity_id, role_entity_id) VALUES (2, 2);
