package com.kaldimitrov.fxplorer.exception;

public class ExchangeRateException extends Exception {
    public ExchangeRateException(String message) {
        super(message);
    }

    public ExchangeRateException(String message, Throwable cause) {
        super(message, cause);
    }
}
