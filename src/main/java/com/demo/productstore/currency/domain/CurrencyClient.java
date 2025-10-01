package com.demo.productstore.currency.domain;

import com.demo.productstore.currency.model.CurrencyCountryCode;

/**
 * Client interface for fetching currency exchange rates.
 */
public interface CurrencyClient {

    /**
     * Fetches the mid-market exchange rate for the specified currency.
     *
     * @param currencyCountryCode the currency country code for which to fetch the exchange rate
     * @return the mid-market exchange rate as a Double
     */
    Double getMidMarketExchangeRate(CurrencyCountryCode currencyCountryCode);

}
