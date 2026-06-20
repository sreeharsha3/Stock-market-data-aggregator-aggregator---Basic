package com.definedge.stock.util;

import com.definedge.stock.entity.StockCandle;
import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class CsvLoader {

    private final List<StockCandle> candles = new ArrayList<>();

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PostConstruct
    public void loadData() {

        try (CSVReader reader = new CSVReader(
                new InputStreamReader(
                        new ClassPathResource("data/stock_data.csv")
                                .getInputStream()))) {

            String[] row;

            // Skip header
            reader.readNext();

            while ((row = reader.readNext()) != null) {

                StockCandle candle =
                        StockCandle.builder()
                                .symbol(row[0])
                                .datetime(LocalDateTime.parse(row[1], FORMATTER))
                                .open(new BigDecimal(row[2]))
                                .high(new BigDecimal(row[3]))
                                .low(new BigDecimal(row[4]))
                                .close(new BigDecimal(row[5]))
                                .volume(Long.parseLong(row[6]))
                                .build();

                candles.add(candle);
            }

            System.out.println("Loaded candles: " + candles.size());

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Failed to load stock_data.csv", ex);
        }
    }
}
