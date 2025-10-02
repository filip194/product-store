package com.demo.productstore.product.controller;

import com.demo.productstore.apisupport.dto.ProductCreateDto;
import com.demo.productstore.apisupport.dto.ProductDto;
import com.demo.productstore.apisupport.dto.ProductUpdateDto;
import com.demo.productstore.apisupport.exception.ApiResponse;
import com.demo.productstore.product.domain.ProductServiceDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductServiceDomain service;

    @InjectMocks
    private ProductController controller;

    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        productDto = mock(ProductDto.class);
    }

    @Test
    void shouldCreateProduct_201Created() {
        final ProductCreateDto createDto = mock(ProductCreateDto.class);
        when(createDto.getCode()).thenReturn("ARG1234567");
        when(service.createProduct(createDto)).thenReturn(productDto);

        final ResponseEntity<ApiResponse<ProductDto>> response = controller.createProduct(createDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        if (responseAvailable(response.getBody())) {
            assertSame(productDto, response.getBody().getResponse());
        }
        verify(service).createProduct(createDto);
    }

    @Test
    void shouldGetProductByCode_200Ok() {
        when(service.getProductByCode("P100")).thenReturn(productDto);

        final ResponseEntity<ApiResponse<ProductDto>> response = controller.getProductByCode("P100");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        if (responseAvailable(response.getBody())) {
            assertSame(productDto, response.getBody().getResponse());
        }
        verify(service).getProductByCode("P100");
    }

    @Test
    void shouldGetAllProducts_200Ok() {
        final Pageable pageable = PageRequest.of(0, 5);
        final Collection<ProductDto> list = List.of(productDto, mock(ProductDto.class));
        when(service.getAllProducts(pageable)).thenReturn(list);

        final ResponseEntity<ApiResponse<Collection<ProductDto>>> response = controller.getAllProducts(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        if (responseCollectionAvailable(response.getBody())) {
            assertEquals(2, response.getBody().getResponse().size());
        }
        verify(service).getAllProducts(pageable);
    }

    @Test
    void shouldUpdateProductByCode_200Ok() {
        final ProductUpdateDto updateDto = mock(ProductUpdateDto.class);
        when(service.updateProductByCode("PX12345678", updateDto)).thenReturn(productDto);

        final ResponseEntity<ApiResponse<ProductDto>> response = controller.updateProductByCode("PX12345678", updateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        if (responseAvailable(response.getBody())) {
            assertSame(productDto, response.getBody().getResponse());
        }
        verify(service).updateProductByCode("PX12345678", updateDto);
    }

    @Test
    void shouldDeleteProductByCode_200Ok() {
        when(service.deleteProductByCode("DEL1234567")).thenReturn(true);

        final ResponseEntity<Void> response = controller.deleteProductByCode("DEL1234567");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service).deleteProductByCode("DEL1234567");
    }

    private boolean responseAvailable(ApiResponse<ProductDto> apiResponse) {
        try {
            apiResponse.getClass().getMethod("getResponse");
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private boolean responseCollectionAvailable(ApiResponse<Collection<ProductDto>> apiResponse) {
        try {
            apiResponse.getClass().getMethod("getResponse");
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}
