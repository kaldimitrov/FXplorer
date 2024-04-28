package com.kaldimitrov.fxplorer.exchange.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "exchange_history")
@NoArgsConstructor
public class ExchangeHistory {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(name = "exchange_rate_id", nullable = false)
    private Long exchangeRateId;

    @Column(name = "input", nullable = false)
    private BigDecimal input;

    @Column(name = "output", nullable = false)
    private BigDecimal output;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exchange_rate_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ExchangeRate exchangeRate;

    public ExchangeHistory(Long exchangeRateId, BigDecimal input, BigDecimal output) {
        this.exchangeRateId = exchangeRateId;
        this.input = input;
        this.output = output;
        this.createdAt = LocalDateTime.now();
    }
}
