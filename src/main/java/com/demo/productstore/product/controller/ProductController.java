package com.demo.productstore.product.controller;

import com.demo.productstore.apisupport.exception.ApiResponse;
import com.demo.productstore.apisupport.dto.ProductCreateDto;
import com.demo.productstore.apisupport.dto.ProductDto;
import com.demo.productstore.apisupport.dto.ProductUpdateDto;
import com.demo.productstore.product.domain.ProductServiceDomain;
import com.demo.productstore.security.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

/**
 * Controller for managing products in the system.
 * <p>
 * This class provides APIs for creating, retrieving, updating, and deleting products.
 * </p>
 */
@RestController
@RequestMapping(path = "/api/v1/product", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "products", description = "Product management API")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductServiceDomain service;

    /**
     * Creates a new product.
     *
     * @param body the product creation details
     * @return ResponseEntity containing the created product details
     */
    @RolesAllowed(UserRole.AUTHENTICATED_USER_ROLE)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new product", security = @SecurityRequirement(name = "basicScheme"))
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@RequestBody ProductCreateDto body) {
        log.info("Received request to create product with code '{}'", body.getCode());
        var response = service.createProduct(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.with(response));
    }

    /**
     * Retrieves a product by its code.
     *
     * @param code the product code
     * @return ResponseEntity containing the product details
     */
    @RolesAllowed(UserRole.AUTHENTICATED_USER_ROLE)
    @GetMapping("/{code}")
    @Operation(summary = "Get product by code", security = @SecurityRequirement(name = "basicScheme"))
    public ResponseEntity<ApiResponse<ProductDto>> getProductByCode(@PathVariable("code") String code) {
        log.info("Received request to get product with code '{}'", code);
        var response = service.getProductByCode(code);
        return ResponseEntity.ok(ApiResponse.with(response));
    }

    /**
     * Retrieves all products with optional pagination.
     *
     * @param page_size  the number of products per page (default is 50)
     * @param page_index the page index (default is 0)
     * @return ResponseEntity containing a collection of product details
     */
    @RolesAllowed(UserRole.AUTHENTICATED_USER_ROLE)
    @GetMapping
    @Operation(summary = "Get all products", security = @SecurityRequirement(name = "basicScheme"))
    public ResponseEntity<ApiResponse<Collection<ProductDto>>> getAllProducts(
            @RequestParam(value = "page_size", required = false) Integer page_size,
            @RequestParam(value = "page_index", required = false) Integer page_index) {
        log.info("Received request to get all products with page size '{}' and page index '{}'", page_size, page_index);
        var pageSize = Optional.ofNullable(page_size).orElse(50);
        var pageIndex = Optional.ofNullable(page_index).orElse(0);
// TODO pagination object
        var products = service.getAllProducts(pageSize, pageIndex);
        return ResponseEntity.ok(ApiResponse.with(products));
    }

    /**
     * Updates a product by its code.
     *
     * @param code the product code
     * @param body the product update details
     * @return ResponseEntity containing the updated product details
     */
    @RolesAllowed(UserRole.AUTHENTICATED_USER_ROLE)
    @PutMapping(value = "/{code}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update product by code", security = @SecurityRequirement(name = "basicScheme"))
    public ResponseEntity<ApiResponse<ProductDto>> updateProductByCode(
            @PathVariable("code") String code,
            @RequestBody ProductUpdateDto body) {
        log.info("Received request to update product with code '{}'", code);
        var response = service.updateProductByCode(code, body);
        return ResponseEntity.ok(ApiResponse.with(response));
    }

    /**
     * Deletes a product by its code.
     *
     * @param code the product code
     * @return ResponseEntity containing the deleted product details
     */
    @RolesAllowed(UserRole.AUTHENTICATED_USER_ROLE)
    @DeleteMapping(value = "/{code}")
    @Operation(summary = "Delete product by code", security = @SecurityRequirement(name = "basicScheme"))
    public ResponseEntity<ApiResponse<ProductDto>> deleteProductByCode(@PathVariable("code") String code) {
        log.info("Received request to delete product with code '{}'", code);
        var response = service.deleteProductByCode(code);
        return ResponseEntity.ok(ApiResponse.with(response));
    }

}
