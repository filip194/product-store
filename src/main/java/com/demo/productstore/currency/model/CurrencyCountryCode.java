package com.demo.productstore.currency.model;

import org.springframework.util.Assert;

/**
 * Represents a currency country code.
 *
 * @param value the currency country code
 */
public record CurrencyCountryCode(String value) {

    public CurrencyCountryCode {
        Assert.hasText(value, "Country ISO code can not be empty");
    }

    /**
     * Returns the currency country code in uppercase.
     *
     * @return the uppercase currency country code
     */
    public String toUpperCase() {
        return value.toUpperCase();
    }
}
