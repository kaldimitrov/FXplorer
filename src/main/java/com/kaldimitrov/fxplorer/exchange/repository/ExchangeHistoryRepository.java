package com.kaldimitrov.fxplorer.exchange.repository;

import com.kaldimitrov.fxplorer.exchange.model.ExchangeHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeHistoryRepository extends JpaRepository<ExchangeHistory, Long> {
    Page<ExchangeHistory> findAll(Specification<ExchangeHistory> spec, Pageable pageable);
}