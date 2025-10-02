package com.demo.productstore.currency.client;

import com.demo.productstore.apisupport.dto.HnbCurrencyDto;
import com.demo.productstore.currency.domain.CurrencyClient;
import com.demo.productstore.currency.model.CurrencyCountryCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.Objects;

/**
 * Client for fetching currency exchange rates from the Croatian National Bank (HNB) API.
 */
@Service
@Slf4j
public class HnbCurrencyClient implements CurrencyClient {

    public static final String HNB_API_BASE_URL = "https://api.hnb.hr/tecajn-eur";
    public static final String HNB_API_VERSION = "v3";
    public static final String HNB_API_CURRENCY_QUERY_PARAM = "valuta";

    /**
     * Fetches the mid-market exchange rate for the specified currency from the Croatian National Bank (HNB) API.
     *
     * @param currencyCountryCode the currency country code for which to fetch the exchange rate
     * @return the mid-market exchange rate as a Double
     */
    @Override
    @Cacheable(value = "midMarketExchangeRates", key = "#currencyCountryCode.value()", unless = "#result == null")
    public Double getMidMarketExchangeRate(CurrencyCountryCode currencyCountryCode) {
        log.info("Fetching mid market exchange rate for currency: {}", currencyCountryCode.value());
        final var response = callHnbExchangeRateClient(currencyCountryCode);

        if (response.getStatusCode().is2xxSuccessful() && Objects.nonNull(response.getBody()) &&
                Arrays.stream(response.getBody()).findFirst().isPresent()) {

            final var midRate = Arrays.stream(response.getBody()).findFirst().get().getMidRate();
            try {
                double twoDecimals = Double.parseDouble(midRate.replace(',', '.'));
                return Math.floor(twoDecimals * 100) / 100;
            } catch (NumberFormatException ex) {
                log.error("Failed to parse mid market exchange rate: {}", midRate, ex);
                throw new IllegalStateException("HNB API returned invalid mid market exchange rate value: " + midRate);
            }
        }
        log.error("Failed to fetch mid market exchange rate from HNB API for currency: {}. Response: {}",
                currencyCountryCode.value().toUpperCase(), response);
        throw new IllegalStateException("Failed to fetch mid market exchange rate from HNB API for currency: " + currencyCountryCode.value());
    }

    /**
     * Clears the mid-market exchange rates cache daily at midnight.
     * This method is scheduled to run automatically and does not require manual invocation.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @CacheEvict(value = "midMarketExchangeRates", allEntries = true)
    public void clearCache() {
        log.info("Clearing mid market exchange rates cache");
    }

    /**
     * Calls the HNB API to fetch exchange rate information for the specified currency.
     *
     * @param currencyCountryCode the currency country code
     * @return a ResponseEntity containing an array of HnbCurrencyDto objects
     */
    public ResponseEntity<HnbCurrencyDto[]> callHnbExchangeRateClient(CurrencyCountryCode currencyCountryCode) {
        log.info("Calling HNB API for currency exchange rate: {}", currencyCountryCode.value());
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
