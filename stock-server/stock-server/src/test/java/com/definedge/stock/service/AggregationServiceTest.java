package com.definedge.stock.service;

import com.definedge.stock.dto.CandleDto;
import com.definedge.stock.entity.StockCandle;
import com.definedge.stock.enums.TimeFrame;
import com.definedge.stock.util.TimeBucketUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AggregationServiceTest {

    private AggregationService aggregationService;

    @BeforeEach
    void setUp() {
        aggregationService =
                new AggregationService(
                        new TimeBucketUtil());
    }

    @Test
    void shouldAggregateFiveMinuteCandlesCorrectly() {

        List<StockCandle> candles = List.of(

                buildCandle(
                        "2026-01-01T09:15:00",
                        "100",
                        "110",
                        "95",
                        "105",
                        1000L),

                buildCandle(
                        "2026-01-01T09:16:00",
                        "105",
                        "120",
                        "100",
                        "115",
                        2000L),

                buildCandle(
                        "2026-01-01T09:17:00",
                        "115",
                        "118",
                        "90",
                        "100",
                        1500L),

                buildCandle(
                        "2026-01-01T09:18:00",
                        "100",
                        "130",
                        "99",
                        "125",
                        3000L),

                buildCandle(
                        "2026-01-01T09:19:00",
                        "125",
                        "126",
                        "120",
                        "122",
                        2500L)
        );

        List<CandleDto> result =
                aggregationService.aggregate(
                        candles,
                        TimeFrame.M5);

        assertEquals(1, result.size());

        CandleDto candle = result.get(0);

        assertEquals(
                new BigDecimal("100"),
                candle.getOpen());

        assertEquals(
                new BigDecimal("130"),
                candle.getHigh());

        assertEquals(
                new BigDecimal("90"),
                candle.getLow());

        assertEquals(
                new BigDecimal("122"),
                candle.getClose());

        assertEquals(
                10000L,
                candle.getVolume());
    }

    @Test
    void shouldReturnSameCandlesForOneMinuteTimeframe() {

        List<StockCandle> candles = List.of(
                buildCandle(
                        "2026-01-01T09:15:00",
                        "100",
                        "110",
                        "95",
                        "105",
                        1000L)
        );

        List<CandleDto> result =
                aggregationService.aggregate(
                        candles,
                        TimeFrame.M1);

        assertEquals(1, result.size());

        assertEquals(
                new BigDecimal("105"),
                result.get(0).getClose());
    }

    private StockCandle buildCandle(
            String dateTime,
            String open,
            String high,
            String low,
            String close,
            Long volume) {

        return StockCandle.builder()
                .symbol("TCS")
                .datetime(
                        LocalDateTime.parse(
                                dateTime))
                .open(new BigDecimal(open))
                .high(new BigDecimal(high))
                .low(new BigDecimal(low))
                .close(new BigDecimal(close))
                .volume(volume)
                .build();
    }
}