package com.demo.productstore.currency.domain;

import com.demo.productstore.currency.model.CurrencyCountryCode;

public interface CurrencyClient {

    Double getMidMarketExchangeRate(CurrencyCountryCode currencyCountryCode);
}
