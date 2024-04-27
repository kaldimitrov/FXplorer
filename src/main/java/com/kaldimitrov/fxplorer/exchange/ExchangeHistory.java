package com.kaldimitrov.fxplorer.exchange;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "exchange_history")
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

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exchange_rate_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ExchangeRate exchangeRate;
}
