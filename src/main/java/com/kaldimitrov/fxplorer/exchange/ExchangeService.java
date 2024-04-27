package com.kaldimitrov.fxplorer.exchange;

import com.kaldimitrov.fxplorer.exchange.request.ExchangeHistoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@ComponentScan
public class ExchangeService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private ExchangeHistoryRepository exchangeHistoryRepository;

    public BigDecimal getExchangeRate(String baseCurrency, String targetCurrency) {
        System.out.println(baseCurrency + " " + targetCurrency);
        return BigDecimal.ZERO; // Replace with logic to fetch actual exchange rate
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
