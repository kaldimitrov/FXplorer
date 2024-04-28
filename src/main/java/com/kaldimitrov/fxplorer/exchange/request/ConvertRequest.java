package com.kaldimitrov.fxplorer.exchange.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConvertRequest {
    private String base;
    private String target;
    private BigDecimal amount;
}
