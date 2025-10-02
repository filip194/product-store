package com.demo.productstore.product.service;

import com.demo.productstore.TestApplication;
import com.demo.productstore.apisupport.dto.ProductCreateDto;
import com.demo.productstore.apisupport.dto.ProductDto;
import com.demo.productstore.apisupport.dto.ProductUpdateDto;
import com.demo.productstore.currency.domain.CurrencyClient;
import com.demo.productstore.currency.model.CurrencyCountryCode;
import com.demo.productstore.currency.model.Price;
import com.demo.productstore.product.domain.ProductRepositoryDomain;
import com.demo.productstore.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

import static com.demo.productstore.fixture.ProductFixture.createProductCreateDto;
import static com.demo.productstore.fixture.ProductFixture.createProductUpdateDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(
        classes = TestApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductServiceTest {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepositoryDomain repository;

    @MockitoBean
    private CurrencyClient currencyClient;

    @BeforeEach
    void setup() {
        when(currencyClient.getMidMarketExchangeRate(any(CurrencyCountryCode.class)))
                .thenReturn(1.10);
    }

    @Test
    void shouldCreateProduct() {
        final ProductCreateDto createDto = createProductCreateDto("P100000000", new BigDecimal("20.00"));
        final ProductDto result = service.createProduct(createDto);

        assertNotNull(result);
        assertEquals("P100000000", result.getCode());
        assertEquals(new BigDecimal("22.00"), result.getPriceUsd());
        assertTrue(repository.findByCode("P100000000").isPresent());
    }

    @Test
    void shouldGetProductByCode() {
        service.createProduct(createProductCreateDto("P200000000", new BigDecimal("5.00")));
        final ProductDto fetched = service.getProductByCode("P200000000");
        assertEquals("P200000000", fetched.getCode());
    }

    @Test
    void shouldGetProductByCode_notFound() {
        assertThrows(ProductNotFoundException.class, () -> service.getProductByCode("NOPE"));
    }

    @Test
    void shouldListProducts() {
        service.createProduct(createProductCreateDto("PX10000000", new BigDecimal("1.00")));
        service.createProduct(createProductCreateDto("PX20000000", new BigDecimal("2.00")));
        service.createProduct(createProductCreateDto("PX30000000", new BigDecimal("3.00")));
        final Collection<ProductDto> products = service.getAllProducts(org.springframework.data.domain.PageRequest.of(0, 10));
        assertTrue(products.size() >= 3);
    }

    @Test
    void shouldUpdateProductByCode() {
        service.createProduct(createProductCreateDto("P300000000", new BigDecimal("10.00")));

        final ProductUpdateDto updateDto = createProductUpdateDto(new BigDecimal("30.00"));
        final ProductDto updated = service.updateProductByCode("P300000000", updateDto);

        assertEquals("P300000000", updated.getCode());
        assertEquals(new BigDecimal("33.00"), updated.getPriceUsd());
        assertEquals("Updated Name", updated.getName());
    }

    @Test
    void shouldUpdateProductByCode_notFound() {
        final ProductUpdateDto updateDto = createProductUpdateDto(new BigDecimal("40.00"));
        assertThrows(ProductNotFoundException.class, () -> service.updateProductByCode("MISS", updateDto));
    }

    @Test
    void shouldDeleteProductByCode() {
        service.createProduct(createProductCreateDto("P400000000", new BigDecimal("12.00")));

        final ProductDto deleted = service.deleteProductByCode("P400000000");

        assertEquals("P400000000", deleted.getCode());
        assertThrows(ProductNotFoundException.class, () -> service.getProductByCode("P400000000"));
    }

    @Test
    void shouldConvertPriceToUsd() {
        final Price price = service.convertPriceToUsd(new BigDecimal("7.50"), "USD");
        assertEquals(new BigDecimal("8.25"), price.value()); // 7.50 * 1.10
    }

    @Nested
    class EdgeCases {

        @Test
        void shouldCreateProduct_zeroPrice() {
            final ProductCreateDto productCreateDto = createProductCreateDto("P500000000", BigDecimal.ZERO);
            final ProductDto created = service.createProduct(productCreateDto);
            assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP), created.getPriceUsd());
        }
    }
}
