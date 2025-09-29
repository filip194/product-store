package com.demo.productstore.currency.model;

import org.springframework.util.Assert;

public record CurrencyCountryCode(String value) {

    public CurrencyCountryCode {
        Assert.hasText(value, "Country ISO code can not be empty");
    }
}
