package com.demo.productstore.currency.model;

import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Price(BigDecimal value) {

    public Price {Assert.notNull(value, "Price can not be null");
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price can not be negative");
        }
        value = value.setScale(2, RoundingMode.HALF_UP);
    }

    public Price multiplyBy(double multiplyValue) {
        return new Price(value.multiply(new BigDecimal(multiplyValue)));
    }
}
