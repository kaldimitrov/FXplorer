package com.kaldimitrov.fxplorer.exchange.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Data
public class ExchangeHistoryRequest {

    private Optional<UUID> id;
    private Optional<LocalDateTime> startDate;
    private Optional<LocalDateTime> endDate;

    @Min(0)
    private int page = 0;

    @Min(1)
    @Max(100)
    private int pageSize = 10;
}
