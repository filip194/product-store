package com.demo.productstore.fixture;

import com.demo.productstore.apisupport.dto.ProductCreateDto;
import com.demo.productstore.apisupport.dto.ProductUpdateDto;

import java.math.BigDecimal;

public class ProductFixture {

    public static ProductCreateDto createProductCreateDto(String code, BigDecimal eurPrice) {
        final ProductCreateDto dto = new ProductCreateDto();
        dto.setCode(code);
        dto.setName("Test Name " + code);
        dto.setDescription("Desc " + code);
        dto.setPriceEur(eurPrice);
        return dto;
    }

    public static ProductUpdateDto createProductUpdateDto(BigDecimal eurPrice) {
        final ProductUpdateDto dto = new ProductUpdateDto();
        dto.setName("Updated Name");
        dto.setDescription("Updated Desc");
        dto.setPriceEur(eurPrice);
        return dto;
    }

}
