package com.kaldimitrov.fxplorer.rateprovider.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Setter
public class ExchangeRateApiResponse {
    private String result;
    private Map<String, BigDecimal> conversionRates;

    @JsonProperty("result")
    public String getResult() {
        return result;
    }

    @JsonProperty("rates")
    public Map<String, BigDecimal> getConversionRates() {
        return conversionRates;
    }
}
