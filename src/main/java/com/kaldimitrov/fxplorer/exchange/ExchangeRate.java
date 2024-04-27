package com.kaldimitrov.fxplorer.exchange;

import com.kaldimitrov.fxplorer.currency.Currency;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "exchange_rates")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_currency_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Currency sourceCurrency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_currency_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Currency targetCurrency;
}
