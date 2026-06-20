package com.definedge.stock.repository;

import com.definedge.stock.entity.StockCandle;

import java.time.LocalDateTime;
import java.util.List;

public interface CandleRepository {
    List<StockCandle> findBySymbolAndDateRange(
            String symbol,
            LocalDateTime start,
            LocalDateTime end);
}
