# Product Store

(Spring Boot 3 with Maven application)

---

## Table of contents

- [General Info](#general-info)
- [Running application](#running-application)
- [Logging into application](#logging-into-application)
- [API Exposure](#api-exposure)
- [Storage](#storage)
- [Security](#security)
- [Tech stack](#tech-stack)

---

### General info

This application provides one API: _product_.

**product** API enables product registration, search, update and deletion.

#### System requirements

- Docker

### Running application

- open project root directory in terminal
- navigate to directory `docker`
- run command: 
  - `docker compose up`
  - it will start PostgreSQL database in Docker container and will run Spring Boot app
- application will be available at:
  - http://localhost:8080
- Swagger UI will be available at:
  - http://localhost:8080/swagger-ui
- to stop application press `CTRL + C` in terminal
- to stop docker containers run:
  - `docker compose down`

#### If Postgres in Docker won't start

- In some cases Postgres in Docker won't be able to load script: `product-store/docker/database/scripts/01-init.sh`, and it won't start properly.
- To fix this, you need to got to the end of `01-init.sh` file where `EOSQL` line is, and add new line after it, or remove line after it.
- It seems like CR needs to be replaced with LF, or vice versa.

#### Run configurations

There are also two run configurations available for development (if using IntelliJ IDEA):

- App - postgresdev - runs application with PostgreSQL database
- App - h2dev - runs application with H2 in-memory database

### Logging into application

This API is governed by security roles and as such can only be accessed by specific users.

These users are both granted all roles when they log in. The idea was to have at least one admin and one user if some necessity shows itself later in the development.

Users can log in using the following credentials:
- Admin: [admin, 123]
- User: [user, 123]

### API exposure

Visit:
 - http://localhost:8080/swagger-ui

This is the base url for the API and here you can login clicking on Authorize button.

### Storage

Product Store is using H2 in-memory database for developing purposes, and PostgreSQL database while running docker compose.
Flyway database migration is used to migrate databases between versions/updates.

### Security

Security is implemented as basic security with usage of Bcrypt for storing passwords.
There are two types of users:

- USER
- ADMIN

Both users have access to product API to create, read, update, and delete products.

### Tech stack

- Spring Boot 3
- Maven
- Lombok
- Database layer:
  - Spring Data JPA with Hibernate
  - PostgreSQL database
  - H2 in-memory database
  - Flyway database migration
- Virtualization:
    - Docker (used with PostgreSQL)
- API exposure:
    - SpringDoc Swagger OpenAPI Specification 3
- Testing:
    - JUnit 5
    - Mockito
- Logging:
    - SLF4J with Logback
