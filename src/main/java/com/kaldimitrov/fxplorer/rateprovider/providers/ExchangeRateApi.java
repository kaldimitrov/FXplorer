package com.kaldimitrov.fxplorer.rateprovider.providers;

import com.kaldimitrov.fxplorer.exception.ExchangeRateException;
import com.kaldimitrov.fxplorer.rateprovider.ExchangeRateService;
import com.kaldimitrov.fxplorer.rateprovider.response.ExchangeRateApiResponse;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

public class ExchangeRateApi implements ExchangeRateService {

    @Override
    public Map<String, BigDecimal> fetchExchangeRates(String baseCurrency) throws ExchangeRateException {
        String apiUrl = "https://open.er-api.com/v6/latest/" + baseCurrency;

        RestTemplate restTemplate = new RestTemplate();
        ExchangeRateApiResponse response = restTemplate.getForObject(apiUrl, ExchangeRateApiResponse.class);

        if (response == null || !response.getResult().equals("success")) {
            throw new ExchangeRateException("Failed to fetch exchange rates for " + baseCurrency);
        }

        return response.getConversionRates();
    }
}
