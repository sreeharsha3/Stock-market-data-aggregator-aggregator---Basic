package com.definedge.stock.service;

import com.definedge.stock.dto.CandleDto;
import com.definedge.stock.entity.StockCandle;
import com.definedge.stock.enums.TimeFrame;
import com.definedge.stock.util.TimeBucketUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.TreeMap;
import java.util.Comparator;


@Service
@RequiredArgsConstructor
public class AggregationService {
    private final TimeBucketUtil bucketUtil;

    public List<CandleDto> aggregate(
            List<StockCandle> candles,
            TimeFrame timeframe) {

        if (timeframe == TimeFrame.M1) {

            return candles.stream()
                    .map(this::toDto)
                    .toList();
        }

        Map<LocalDateTime, List<StockCandle>> grouped =
                candles.stream()
                        .collect(Collectors.groupingBy(
                                candle ->
                                        bucketUtil.bucket(
                                                candle.getDatetime(),
                                                timeframe),
                                TreeMap::new,
                                Collectors.toList()));

        return grouped.entrySet()
                .stream()
                .map(entry -> buildAggregate(
                        (Map.Entry<LocalDateTime, List<StockCandle>>) entry))
                .toList();
    }

    private CandleDto buildAggregate(
            Map.Entry<LocalDateTime, List<StockCandle>> entry) {

        List<StockCandle> bucketCandles =
                entry.getValue();

        bucketCandles.sort(
                Comparator.comparing(
                        StockCandle::getDatetime));

        StockCandle first =
                bucketCandles.get(0);

        StockCandle last =
                bucketCandles.get(
                        bucketCandles.size() - 1);

        BigDecimal high =
                bucketCandles.stream()
                        .map(StockCandle::getHigh)
                        .max(BigDecimal::compareTo)
                        .orElse(BigDecimal.ZERO);

        BigDecimal low =
                bucketCandles.stream()
                        .map(StockCandle::getLow)
                        .min(BigDecimal::compareTo)
                        .orElse(BigDecimal.ZERO);

        long volume =
                bucketCandles.stream()
                        .mapToLong(
                                StockCandle::getVolume)
                        .sum();

        return CandleDto.builder()
                .datetime(
                        (java.time.LocalDateTime)
                                entry.getKey())
                .open(first.getOpen())
                .high(high)
                .low(low)
                .close(last.getClose())
                .volume(volume)
                .build();
    }

    private CandleDto toDto(
            StockCandle candle) {

        return CandleDto.builder()
                .datetime(candle.getDatetime())
                .open(candle.getOpen())
                .high(candle.getHigh())
                .low(candle.getLow())
                .close(candle.getClose())
                .volume(candle.getVolume())
                .build();
    }
}
