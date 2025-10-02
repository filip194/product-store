package com.demo.productstore.currency.client;

import com.demo.productstore.apisupport.dto.HnbCurrencyDto;
import com.demo.productstore.currency.model.CurrencyCountryCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HnbCurrencyClientTest {


    @Test
    void shouldGetMidMarketExchangeRate_ReturnsParsedDouble_OnValidResponse() {
        final HnbCurrencyDto dto = new HnbCurrencyDto();
        dto.setMidRate("1,172400");
        final ResponseEntity<HnbCurrencyDto[]> response = new ResponseEntity<>(new HnbCurrencyDto[]{dto}, HttpStatus.OK);

        final HnbCurrencyClient client = spy(new HnbCurrencyClient());
        doReturn(response).when(client).callHnbExchangeRateClient(any(CurrencyCountryCode.class));

        final Double rate = client.getMidMarketExchangeRate(new CurrencyCountryCode("USD"));
        assertEquals(1.17, rate);
    }

    @Test
    void shouldGetMidMarketExchangeRate_ThrowsOnInvalidNumber() {
        final HnbCurrencyDto dto = new HnbCurrencyDto();
        dto.setMidRate("invalid");
        final ResponseEntity<HnbCurrencyDto[]> response = new ResponseEntity<>(new HnbCurrencyDto[]{dto}, HttpStatus.OK);
        final HnbCurrencyClient client = spy(new HnbCurrencyClient());
        doReturn(response).when(client).callHnbExchangeRateClient(any(CurrencyCountryCode.class));

        final IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> client.getMidMarketExchangeRate(new CurrencyCountryCode("USD")));
        assertTrue(ex.getMessage().toLowerCase().contains("invalid mid market exchange rate"));
    }

    @Test
    void shouldGetMidMarketExchangeRate_ThrowsOnEmptyResponse() {
        final ResponseEntity<HnbCurrencyDto[]> response = new ResponseEntity<>(new HnbCurrencyDto[]{}, HttpStatus.OK);

        final HnbCurrencyClient client = spy(new HnbCurrencyClient());
        doReturn(response).when(client).callHnbExchangeRateClient(any(CurrencyCountryCode.class));

        final IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> client.getMidMarketExchangeRate(new CurrencyCountryCode("USD")));
        assertTrue(ex.getMessage().toLowerCase().contains("failed to fetch mid market exchange rate"));
    }

    @Test
    void shouldGetMidMarketExchangeRate_ThrowsOnNon2xxResponse() {
        final ResponseEntity<HnbCurrencyDto[]> response = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        final HnbCurrencyClient client = spy(new HnbCurrencyClient());
        doReturn(response).when(client).callHnbExchangeRateClient(any(CurrencyCountryCode.class));

        final IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> client.getMidMarketExchangeRate(new CurrencyCountryCode("USD")));
        assertTrue(ex.getMessage().toLowerCase().contains("failed to fetch mid market exchange rate"));
    }
}
