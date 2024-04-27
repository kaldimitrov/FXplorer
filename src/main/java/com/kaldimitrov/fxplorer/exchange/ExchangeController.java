package com.kaldimitrov.fxplorer.exchange;

import com.kaldimitrov.fxplorer.exchange.request.ExchangeHistoryRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@RequestMapping("exchanges")
public class ExchangeController {

    private final ExchangeService service;

    @GetMapping
    public ResponseEntity<BigDecimal> getExchangeRate(@RequestParam String base, @RequestParam String target) {
        return ResponseEntity.ok(service.getExchangeRate(base, target));
    }

    @GetMapping("history")
    public ResponseEntity<Page<ExchangeHistory>> getExchangeHistory(ExchangeHistoryRequest request) {
        return ResponseEntity.ok(this.service.getExchangeHistory(request));
    }

    @GetMapping("history/{id}")
    public ResponseEntity<ExchangeHistory> getHistoryById(@PathVariable String id) {
        return ResponseEntity.ok(null);
    }
}