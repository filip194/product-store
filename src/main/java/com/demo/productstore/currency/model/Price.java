package com.demo.productstore.currency.model;

import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a price with a monetary value.
 *
 * @param value the monetary value of the price
 */
public record Price(BigDecimal value) {

    public Price {
        Assert.notNull(value, "Price can not be null");

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price can not be negative");
        }
        value = value.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Multiplies the price by a given value.
     *
     * @param multiplyValue the value to multiply the price by
     * @return a new Price instance with the multiplied value
     */
    public Price multiplyBy(double multiplyValue) {
        return new Price(value.multiply(new BigDecimal(multiplyValue)));
    }
}
