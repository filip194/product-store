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

    private final static String HNB_RESPONSE_OK = "[{\"broj_tecajnice\":\"193\",\"datum_primjene\":\"2025-10-02\",\"drzava\":\"SAD\",\"drzava_iso\":\"USA\",\"kupovni_tecaj\":\"1,174200\",\"prodajni_tecaj\":\"1,170600\",\"sifra_valute\":\"840\",\"srednji_tecaj\":\"1,172400\",\"valuta\":\"USD\"}]";
    private final static String HNB_RESPONSE_INVALID_NUMBER = "[{\"broj_tecajnice\":\"193\",\"datum_primjene\":\"2025-10-02\",\"drzava\":\"SAD\",\"drzava_iso\":\"USA\",\"kupovni_tecaj\":\"1,174200\",\"prodajni_tecaj\":\"1,170600\",\"sifra_valute\":\"840\",\"srednji_tecaj\":\"invalid\",\"valuta\":\"USD\"}]";
    private final static String HNB_RESPONSE_EMPTY = "[]";

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
                .willReturn(okJson(HNB_RESPONSE_OK)));

        final Double rate = client.getMidMarketExchangeRate(new CurrencyCountryCode("USD"));
        assertEquals(1.17, rate);
    }

    @Test
    void getMidMarketExchangeRate_ThrowsOnInvalidNumber() {
        stubFor(get(urlPathEqualTo("/v3"))
                .withQueryParam("valuta", equalTo("USD"))
                .willReturn(okJson(HNB_RESPONSE_INVALID_NUMBER)));

        final IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                client.getMidMarketExchangeRate(new CurrencyCountryCode("USD")));
        assertTrue(ex.getMessage().contains("invalid mid market exchange rate value"));
    }

    @Test
    void getMidMarketExchangeRate_ThrowsOnEmptyResponse() {
        stubFor(get(urlPathEqualTo("/v3"))
                .withQueryParam("valuta", equalTo("USD"))
                .willReturn(okJson(HNB_RESPONSE_EMPTY)));

        final IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                client.getMidMarketExchangeRate(new CurrencyCountryCode("USD")));
        assertTrue(ex.getMessage().contains("Failed to fetch mid market exchange rate"));
    }

}
