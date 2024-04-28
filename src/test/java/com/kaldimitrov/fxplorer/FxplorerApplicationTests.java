package com.kaldimitrov.fxplorer;

import com.kaldimitrov.fxplorer.exception.ExchangeRateException;
import com.kaldimitrov.fxplorer.exchange.ExchangeService;
import com.kaldimitrov.fxplorer.provider.ExchangeRateProviderApi;
import com.kaldimitrov.fxplorer.provider.ExchangeRateProviderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FxplorerApplicationTests {

    @Autowired
    private ExchangeService exchangeService;

    @Test
    public void testFetchExchangeRatesNotEmpty() throws ExchangeRateException {
        ExchangeRateProviderService exchangeRateProviderService = new ExchangeRateProviderApi();
        String baseCurrency = "USD";

        Map<String, BigDecimal> exchangeRates = exchangeRateProviderService.fetchExchangeRates(baseCurrency);

        assertFalse(exchangeRates.isEmpty(), "Exchange rates map should not be empty");
    }

    @Test
    public void testFetchExchangeRate() throws ExchangeRateException {
        String baseCurrency = "USD";
        String targetCurrency = "EUR";

        assertNotEquals(exchangeService.getExchangeRate(baseCurrency, targetCurrency).get().getRate(), BigDecimal.ZERO, "Exchange rate should not be zero");
    }

    @Test
    public void testExchangeRate() throws ExchangeRateException {
        String baseCurrency = "USD";
        String targetCurrency = "EUR";
        BigDecimal exchangeAmount = BigDecimal.valueOf(100);

        BigDecimal rate = exchangeService.getExchangeRate(baseCurrency, targetCurrency).get().getRate();
        assertEquals(rate.multiply(exchangeAmount), exchangeService.convertAmount(exchangeAmount, baseCurrency, targetCurrency).getOutput());
    }

    @Test
    public void testExchangeRateException() {
        String baseCurrency = "USD";
        String targetCurrency = "TEST";

        assertThrows(ExchangeRateException.class, () -> exchangeService.getExchangeRate(baseCurrency, targetCurrency).get().getRate());
    }

    @Test
    public void testReverseExchangeRate() throws ExchangeRateException {
        String baseCurrency = "USD";
        String targetCurrency = "EUR";

        BigDecimal rate = exchangeService.getExchangeRate(baseCurrency, targetCurrency).get().getRate();
        BigDecimal reverseRate = exchangeService.getExchangeRate(targetCurrency, baseCurrency).get().getRate();

        assertEquals(BigDecimal.ONE.divide(rate, 4, RoundingMode.HALF_UP), reverseRate.setScale(4, RoundingMode.HALF_UP), "Reverse exchange rate should match");
    }
}
