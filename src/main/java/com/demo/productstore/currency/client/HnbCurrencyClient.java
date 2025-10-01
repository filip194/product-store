package com.demo.productstore.currency.client;

import com.demo.productstore.apisupport.dto.HnbCurrencyDto;
import com.demo.productstore.currency.domain.CurrencyClient;
import com.demo.productstore.currency.model.CurrencyCountryCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.Objects;

/**
 * Client for fetching currency exchange rates from the Croatian National Bank (HNB) API.
 */
@Service
public class HnbCurrencyClient implements CurrencyClient {

    private static final String HNB_API_BASE_URL = "https://api.hnb.hr/tecajn-eur";
    private static final String HNB_API_VERSION = "v3";
    private static final String HNB_API_CURRENCY_QUERY_PARAM = "valuta";

    /**
     * Fetches the mid-market exchange rate for the specified currency from the Croatian National Bank (HNB) API.
     *
     * @param currencyCountryCode the currency country code for which to fetch the exchange rate
     * @return the mid-market exchange rate as a Double
     */
    @Override
    public Double getMidMarketExchangeRate(CurrencyCountryCode currencyCountryCode) {
        final var response = callHnbExchangeRateClient(currencyCountryCode);

        if (response.getStatusCode().is2xxSuccessful() && Objects.nonNull(response.getBody()) &&
                Arrays.stream(response.getBody()).findFirst().isPresent()) {

            final var midRate = Arrays.stream(response.getBody()).findFirst().get().getMidRate();
            try {
                return Double.valueOf(midRate.replace(',', '.'));
            } catch (NumberFormatException ex) {
                throw new IllegalStateException("HNB API returned invalid mid market exchange rate value: " + midRate);
            }
        }
        throw new IllegalStateException("Failed to fetch mid market exchange rate from HNB API for currency: " + currencyCountryCode.value());
    }

    /**
     * Calls the HNB API to fetch exchange rate information for the specified currency.
     *
     * @param currencyCountryCode the currency country code
     * @return a ResponseEntity containing an array of HnbCurrencyDto objects
     */
    private ResponseEntity<HnbCurrencyDto[]> callHnbExchangeRateClient(CurrencyCountryCode currencyCountryCode) {
        return RestClient.create(HNB_API_BASE_URL)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + HNB_API_VERSION)
                        .queryParam(HNB_API_CURRENCY_QUERY_PARAM, currencyCountryCode.value().toUpperCase())
                        .build())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .toEntity(HnbCurrencyDto[].class);
    }

}
