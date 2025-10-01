package com.demo.productstore.apisupport.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(title = "HNB Currency information")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HnbCurrencyDto {

    @JsonProperty("broj_tecajnice")
    private String exchangeRateNumber;

    @JsonProperty("datum_primjene")
    private String applicationDate;

    @JsonProperty("drzava")
    private String country;

    @JsonProperty("drzava_iso")
    private String countryIso;

    @JsonProperty("kupovni_tecaj")
    private String buyingRate;

    @JsonProperty("prodajni_tecaj")
    private String sellingRate;

    @JsonProperty("sifra_valute")
    private String currencyCode;

    @JsonProperty("srednji_tecaj")
    private String midRate;

    @JsonProperty("valuta")
    private String currency;
}
