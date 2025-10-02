package com.demo.productstore.currency.client;

import com.demo.productstore.TestApplication;
import com.demo.productstore.currency.model.CurrencyCountryCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.wiremock.spring.EnableWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        classes = TestApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock
class HnbCurrencyClientTest {

    @Value("${wiremock.server.port}")
    protected int wiremockPort;

    @Value("${wiremock.server.baseUrl}")
    private String wireMockUrl;

    @Autowired
    private HnbCurrencyClient client;

    @Test
    void getMidMarketExchangeRate_ReturnsParsedDouble() {
        stubFor(get(urlPathEqualTo("/v3"))
                .withQueryParam("valuta", equalTo("USD"))
                .willReturn(okJson("[{\"midRate\":\"7,45\"}]")));

        Double rate = client.getMidMarketExchangeRate(new CurrencyCountryCode("USD"));
        assertEquals(7.45, rate);
    }

    @Test
    void getMidMarketExchangeRate_ThrowsOnInvalidNumber() {
        stubFor(get(urlPathEqualTo("/v3"))
                .withQueryParam("valuta", equalTo("USD"))
                .willReturn(okJson("[{\"midRate\":\"invalid\"}]")));

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                client.getMidMarketExchangeRate(new CurrencyCountryCode("USD")));
        assertTrue(ex.getMessage().contains("invalid mid market exchange rate value"));
    }

    @Test
    void getMidMarketExchangeRate_ThrowsOnEmptyResponse() {
        stubFor(get(urlPathEqualTo("/v3"))
                .withQueryParam("valuta", equalTo("USD"))
                .willReturn(okJson("[]")));

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                client.getMidMarketExchangeRate(new CurrencyCountryCode("USD")));
        assertTrue(ex.getMessage().contains("Failed to fetch mid market exchange rate"));
    }

}
