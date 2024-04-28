package com.kaldimitrov.fxplorer.exchange.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class RateResponse {
    private BigDecimal rate;
}
