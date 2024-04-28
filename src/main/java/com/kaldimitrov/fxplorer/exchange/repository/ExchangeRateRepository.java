package com.kaldimitrov.fxplorer.exchange.repository;

import com.kaldimitrov.fxplorer.exchange.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    Optional<ExchangeRate> findOneBySourceCurrencyIdAndTargetCurrencyIdAndDate(Long sourceCurrencyId, Long targetCurrencyId, Date date);
}