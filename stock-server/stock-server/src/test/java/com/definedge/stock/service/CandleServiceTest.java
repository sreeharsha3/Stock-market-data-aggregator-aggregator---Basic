package com.definedge.stock.service;

import com.definedge.stock.dto.CandleDto;
import com.definedge.stock.dto.CandleResponse;
import com.definedge.stock.entity.StockCandle;
import com.definedge.stock.exceptions.SymbolNotFoundException;
import com.definedge.stock.repository.CandleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CandleServiceTest {

    @Mock
    private CandleRepository repository;

    @Mock
    private AggregationService aggregationService;

    @InjectMocks
    private CandleService candleService;

    @Test
    void shouldReturnAggregatedResponse() {

        List<StockCandle> candles =
                List.of(
                        StockCandle.builder()
                                .symbol("TCS")
                                .build());

        List<CandleDto> aggregated =
                List.of(
                        CandleDto.builder()
                                .close(
                                        new BigDecimal("100"))
                                .build());

        when(repository.findBySymbolAndDateRange(
                anyString(),
                any(),
                any()))
                .thenReturn(candles);

        when(aggregationService.aggregate(
                anyList(),
                any()))
                .thenReturn(aggregated);

        CandleResponse response =
                candleService.getCandles(
                        "TCS",
                        "5m",
                        LocalDateTime.now(),
                        LocalDateTime.now());

        assertEquals(
                "TCS",
                response.getSymbol());

        assertEquals(
                "5m",
                response.getTimeframe());

        assertEquals(
                1,
                response.getCount());

        assertEquals(
                aggregated,
                response.getCandles());
    }

    @Test
    void shouldThrowExceptionWhenSymbolNotFound() {

        when(repository.findBySymbolAndDateRange(
                anyString(),
                any(),
                any()))
                .thenReturn(List.of());

        assertThrows(
                SymbolNotFoundException.class,
                () ->
                        candleService.getCandles(
                                "INVALID",
                                "5m",
                                LocalDateTime.now(),
                                LocalDateTime.now()));
    }
}