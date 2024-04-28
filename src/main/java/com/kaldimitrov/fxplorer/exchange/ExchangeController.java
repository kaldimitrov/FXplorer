package com.kaldimitrov.fxplorer.exchange;

import com.kaldimitrov.fxplorer.exception.ExchangeRateException;
import com.kaldimitrov.fxplorer.exchange.request.ConvertRequest;
import com.kaldimitrov.fxplorer.exchange.request.ExchangeHistoryRequest;
import com.kaldimitrov.fxplorer.exchange.response.RateResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("exchanges")
public class ExchangeController {

    private final ExchangeService service;

    @GetMapping
    public ResponseEntity<?> getExchangeRate(@RequestParam String base, @RequestParam String target) {
        try {
            return ResponseEntity.ok(new RateResponse(service.getExchangeRate(base, target).get().getRate()));
        } catch (ExchangeRateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("convert")
    public ResponseEntity<?> convertAmount(@RequestBody ConvertRequest convertRequest) {
        try {
            return ResponseEntity.ok(this.service.convertAmount(convertRequest.getAmount(), convertRequest.getBase(), convertRequest.getTarget()));
        } catch (ExchangeRateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("history")
    public ResponseEntity<Page<ExchangeHistory>> getExchangeHistory(ExchangeHistoryRequest request) {
        return ResponseEntity.ok(this.service.getExchangeHistory(request));
    }
}