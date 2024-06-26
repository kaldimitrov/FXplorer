package com.kaldimitrov.fxplorer.currency;

import com.kaldimitrov.fxplorer.currency.model.Currency;
import com.kaldimitrov.fxplorer.currency.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@ComponentScan
public class CurrencyService {
    @Autowired
    private CurrencyRepository currencyRepository;

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    public Optional<Currency> findByCode(String code) {
        return currencyRepository.findByCode(code);
    }

    public Map<String, Long> createCurrencyMap(List<Currency> currencies) {
        Map<String, Long> map = new HashMap<>();
        for (Currency currency : currencies) {
            map.put(currency.getCode(), currency.getId());
        }
        return map;
    }
}