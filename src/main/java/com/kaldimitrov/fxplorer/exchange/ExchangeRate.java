package com.kaldimitrov.fxplorer.exchange;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kaldimitrov.fxplorer.currency.Currency;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "exchange_rates")
@NoArgsConstructor
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_currency_id", nullable = false)
    private Long sourceCurrencyId;

    @Column(name = "target_currency_id", nullable = false)
    private Long targetCurrencyId;

    @Column(name = "rate", nullable = false)
    private BigDecimal rate;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_currency_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Currency sourceCurrency;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "target_currency_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Currency targetCurrency;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exchangeRate")
    private Set<ExchangeHistory> exchangeHistories = new HashSet<>();

    ExchangeRate(Long sourceCurrencyId, Long targetCurrencyId, BigDecimal rate) {
        this.sourceCurrencyId = sourceCurrencyId;
        this.targetCurrencyId = targetCurrencyId;
        this.rate = rate;
        this.date = new Date();
    }
}
