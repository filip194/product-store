package com.demo.productstore.currency.client;

import com.demo.productstore.apisupport.model.CurrencyDto;
import com.demo.productstore.currency.domain.CurrencyClient;
import com.demo.productstore.currency.model.CurrencyCountryCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.Objects;

@Service
public class HnbCurrencyClient implements CurrencyClient {

    private static final String HNB_API_BASE_URL = "https://api.hnb.hr/tecajn-eur";
    private static final String HNB_API_VERSION = "v3";
    private static final String HNB_API_CURRENCY_QUERY_PARAM = "valuta";

    @Override
    public Double getMidMarketExchangeRate(CurrencyCountryCode currencyCountryCode) {
        final var restClient = createRestClient();
        final var response = getClientResponse(restClient, currencyCountryCode);

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

    private RestClient createRestClient() {
        return RestClient.create(HNB_API_BASE_URL);
    }

    private ResponseEntity<CurrencyDto[]> getClientResponse(RestClient restClient, CurrencyCountryCode currencyCountryCode) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + HNB_API_VERSION)
                        .queryParam(HNB_API_CURRENCY_QUERY_PARAM, currencyCountryCode.value())
                        .build())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .toEntity(CurrencyDto[].class);
    }

}
