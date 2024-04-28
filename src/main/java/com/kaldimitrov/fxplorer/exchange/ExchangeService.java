package com.kaldimitrov.fxplorer.exchange;

import com.kaldimitrov.fxplorer.currency.CurrencyService;
import com.kaldimitrov.fxplorer.currency.model.Currency;
import com.kaldimitrov.fxplorer.exception.ExchangeRateException;
import com.kaldimitrov.fxplorer.exchange.model.ExchangeHistory;
import com.kaldimitrov.fxplorer.exchange.model.ExchangeRate;
import com.kaldimitrov.fxplorer.exchange.repository.ExchangeHistoryRepository;
import com.kaldimitrov.fxplorer.exchange.repository.ExchangeRateRepository;
import com.kaldimitrov.fxplorer.exchange.request.ExchangeHistoryRequest;
import com.kaldimitrov.fxplorer.provider.ExchangeRateProviderApi;
import com.kaldimitrov.fxplorer.provider.ExchangeRateProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@ComponentScan
public class ExchangeService {

    private final ExchangeRateProviderService exchangeRateProviderService = new ExchangeRateProviderApi();
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;
    @Autowired
    private ExchangeHistoryRepository exchangeHistoryRepository;
    @Autowired
    private CurrencyService currencyService;

    public Optional<ExchangeRate> getExchangeRate(String baseCurrencyCode, String targetCurrencyCode) throws ExchangeRateException {
        Optional<Currency> baseCurrency = currencyService.findByCode(baseCurrencyCode);
        if (baseCurrency.isEmpty()) {
            throw new ExchangeRateException("No currency found for code " + baseCurrencyCode);
        }

        Optional<Currency> targetCurrency = currencyService.findByCode(targetCurrencyCode);
        if (targetCurrency.isEmpty()) {
            throw new ExchangeRateException("No currency found for code " + targetCurrencyCode);
        }

        Optional<ExchangeRate> rate = exchangeRateRepository.findOneBySourceCurrencyIdAndTargetCurrencyIdAndDate(baseCurrency.get().getId(), targetCurrency.get().getId(), new Date());
        if (rate.isPresent()) {
            return rate;
        }

        Map<String, BigDecimal> rates = exchangeRateProviderService.fetchExchangeRates(baseCurrencyCode);
        if (rates.get(targetCurrencyCode) == null) {
            throw new ExchangeRateException("No currency found for code " + targetCurrencyCode);
        }

        Map<String, Long> currencyMap = currencyService.createCurrencyMap(currencyService.findAll());
        for (Map.Entry<String, BigDecimal> entry : rates.entrySet()) {
            try {
                exchangeRateRepository.save(new ExchangeRate(baseCurrency.get().getId(), currencyMap.get(entry.getKey()), entry.getValue()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return exchangeRateRepository.findOneBySourceCurrencyIdAndTargetCurrencyIdAndDate(baseCurrency.get().getId(), targetCurrency.get().getId(), new Date());
    }

    public ExchangeHistory convertAmount(BigDecimal amount, String baseCurrencyCode, String targetCurrencyCode) throws ExchangeRateException {
        Optional<ExchangeRate> rate = this.getExchangeRate(baseCurrencyCode, targetCurrencyCode);
        BigDecimal exchangeAmount = rate.get().getRate().multiply(amount);

        return this.exchangeHistoryRepository.save(new ExchangeHistory(rate.get().getId(), amount, exchangeAmount));
    }

    public Page<ExchangeHistory> getExchangeHistory(ExchangeHistoryRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getPageSize());

        Specification<ExchangeHistory> spec = buildExchangeHistorySpec(request);
        return exchangeHistoryRepository.findAll(spec, pageable);
    }

    private Specification<ExchangeHistory> buildExchangeHistorySpec(ExchangeHistoryRequest request) {
        Specification<ExchangeHistory> spec = null;

        if (request.getId() != null) {
            spec = (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("id"), request.getId().get());
        }

        if (request.getStartDate() != null) {
            spec = spec == null ? (root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), request.getStartDate().get()) :
                    spec.and((root, query, criteriaBuilder) ->
                            criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), request.getStartDate().get()));
        }

        if (request.getEndDate() != null) {
            spec = spec == null ? (root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), request.getEndDate().get()) :
                    spec.and((root, query, criteriaBuilder) ->
                            criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), request.getEndDate().get()));
        }

        return spec;
    }
}
