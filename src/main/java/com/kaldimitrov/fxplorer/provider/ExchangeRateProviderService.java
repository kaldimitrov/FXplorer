package com.kaldimitrov.fxplorer.provider;

import com.kaldimitrov.fxplorer.exception.ExchangeRateException;

import java.math.BigDecimal;
import java.util.Map;

public interface ExchangeRateProviderService {
    /**
     * Fetches exchange rates for the given base currency.
     *
     * @param baseCurrency The currency for which exchange rates are fetched.
     * @return A map where keys are currency codes and values are exchange rates relative to the base currency.
     * @throws ExchangeRateException If there's an error fetching exchange rates.
     */
    Map<String, BigDecimal> fetchExchangeRates(String baseCurrency) throws ExchangeRateException;
}
