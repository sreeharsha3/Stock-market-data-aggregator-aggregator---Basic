package com.definedge.stock.service;

import com.definedge.stock.dto.CandleDto;
import com.definedge.stock.dto.CandleResponse;
import com.definedge.stock.entity.StockCandle;
import com.definedge.stock.enums.TimeFrame;
import com.definedge.stock.exceptions.SymbolNotFoundException;
import com.definedge.stock.repository.CandleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandleService {
    private final CandleRepository repository;

    private final AggregationService aggregationService;

    public CandleResponse getCandles(
            String symbol,
            String timeframe,
            LocalDateTime start,
            LocalDateTime end) {

        TimeFrame tf = TimeFrame.from(timeframe);

        List<StockCandle> candles =
                repository.findBySymbolAndDateRange(
                        symbol,
                        start,
                        end);

        if (candles.isEmpty()) {
            throw new SymbolNotFoundException(symbol);
        }

        List<CandleDto> result =
                aggregationService.aggregate(
                        candles,
                        tf);

        return CandleResponse.builder()
                .symbol(symbol)
                .timeframe(timeframe)
                .count(result.size())
                .candles(result)
                .build();
    }
}
