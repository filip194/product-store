package com.demo.productstore.product.controller;

import com.demo.productstore.apisupport.exception.ApiResponse;
import com.demo.productstore.apisupport.model.ProductCreateDto;
import com.demo.productstore.apisupport.model.ProductDto;
import com.demo.productstore.apisupport.model.ProductUpdateDto;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/product", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "products", description = "Product management API")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductServiceDomain service;

    @SecurityRequirement(name = "basicAuth")
    @RolesAllowed(UserRole.AUTHENTICATED_USER_ROLE)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new product")
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@RequestBody ProductCreateDto body) {
        var response = service.createProduct(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.with(response));
    }

    @SecurityRequirement(name = "basicAuth")
    @RolesAllowed(UserRole.AUTHENTICATED_USER_ROLE)
    @GetMapping("/{code}")
    @Operation(summary = "Get product by code")
    public ResponseEntity<ApiResponse<ProductDto>> getProductByCode(@PathVariable("code") String code) {
        var response = service.getProductByCode(code);
        return ResponseEntity.ok(ApiResponse.with(response));
    }

    @SecurityRequirement(name = "basicAuth")
    @RolesAllowed(UserRole.AUTHENTICATED_USER_ROLE)
    @GetMapping
    @Operation(summary = "Get all products")
    public ResponseEntity<ApiResponse<Collection<ProductDto>>> getAllProducts(
            @RequestParam(value = "page_size", required = false) Integer page_size,
            @RequestParam(value = "page_index", required = false) Integer page_index) {
        var pageSize = Optional.ofNullable(page_size).orElse(50);
        var pageIndex = Optional.ofNullable(page_index).orElse(0);

        var products = service.getAllProducts(pageSize, pageIndex);
        return ResponseEntity.ok(ApiResponse.with(products));
    }

    @SecurityRequirement(name = "basicAuth")
    @RolesAllowed(UserRole.AUTHENTICATED_USER_ROLE)
    @PutMapping(value = "/{code}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update product by code")
    public ResponseEntity<ApiResponse<ProductDto>> updateProductByCode(
            @PathVariable("code") String code,
            @RequestBody ProductUpdateDto body) {
        var response = service.updateProductByCode(code, body);
        return ResponseEntity.ok(ApiResponse.with(response));
    }

    @SecurityRequirement(name = "basicAuth")
    @RolesAllowed(UserRole.AUTHENTICATED_USER_ROLE)
    @DeleteMapping(value = "/{code}")
    @Operation(summary = "Delete product by code")
    public ResponseEntity<ApiResponse<ProductDto>> deleteProductByCode(@PathVariable("code") String code) {
        var response = service.deleteProductByCode(code);
        return ResponseEntity.ok(ApiResponse.with(response));
    }

}
