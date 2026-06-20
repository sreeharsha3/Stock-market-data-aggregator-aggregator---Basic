package com.definedge.stock.repository;

import com.definedge.stock.entity.StockCandle;
import com.definedge.stock.util.CsvLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Comparator;

@Repository
@RequiredArgsConstructor
public class CsvCandleRepository implements CandleRepository{
    private final CsvLoader csvLoader;

    @Override
    public List<StockCandle> findBySymbolAndDateRange(
            String symbol,
            LocalDateTime start,
            LocalDateTime end) {

        return csvLoader.getCandles()
                .stream()
                .filter(c ->
                        c.getSymbol()
                                .equalsIgnoreCase(symbol))
                .filter(c ->
                        !c.getDatetime().isBefore(start))
                .filter(c ->
                        !c.getDatetime().isAfter(end))
                .sorted(
                        Comparator.comparing(
                                StockCandle::getDatetime))
                .toList();
    }
}
