package com.kaldimitrov.fxplorer.exchange;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("exchanges")
public class ExchangeController {

    private final ExchangeService service;
}