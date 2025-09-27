package com.demo.productstore.product.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/product", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "products", description = "Product management API")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

//    private final ProductService productService;
//
//
//    @PostMapping
//    public ResponseEntity<ApiResponse<ProductDto>> createProduct(
//            @RequestBody ProductCreateRequest request) {
//        var result = productCreateUseCase.createProduct(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.of(result));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ApiResponse<ProductDto>> getProduct(@PathVariable("id") String id) {
//        var product = productFetchUseCase.getProduct(new ProductExternalId(UUID.fromString(id)));
//        return ResponseEntity.ok(ApiResponse.of(product));
//    }
//
//    @GetMapping
//    public ResponseEntity<ApiResponse<List<ProductDto>>> getProducts(
//            @RequestParam(value = "page_index", required = false) Integer pageIndex,
//            @RequestParam(value = "page_size", required = false) Integer pageSize) {
//        var page = Optional.ofNullable(pageIndex).orElse(0);
//        var size = Optional.ofNullable(pageSize).orElse(100);
//
//        var products = productFetchUseCase.getProducts(page, size);
//        return ResponseEntity.ok(ApiResponse.of(products));
//    }
}
