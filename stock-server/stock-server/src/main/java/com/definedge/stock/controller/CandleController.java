package com.definedge.stock.controller;

import com.definedge.stock.dto.CandleResponse;
import com.definedge.stock.service.CandleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/candles")
@RequiredArgsConstructor
public class CandleController {
    private final CandleService service;

    @Operation(
            summary = "Get aggregated stock candles",
            description = "Returns OHLCV candle data aggregated to requested timeframe"
    )
    @GetMapping
    public CandleResponse getCandles(

            @RequestParam String symbol,

            @RequestParam String timeframe,

            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam LocalDateTime start,

            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam LocalDateTime end) {

        return service.getCandles(
                symbol,
                timeframe,
                start,
                end);
    }
}
