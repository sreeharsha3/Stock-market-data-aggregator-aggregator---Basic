package com.definedge.stock.util;

import com.definedge.stock.enums.TimeFrame;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimeBucketUtil {
    public LocalDateTime bucket(
            LocalDateTime dateTime,
            TimeFrame timeframe) {

        if (timeframe == TimeFrame.D1) {

            return dateTime
                    .toLocalDate()
                    .atStartOfDay();
        }

        int minutes = timeframe.getMinutes();

        int bucketMinute =
                (dateTime.getMinute() / minutes)
                        * minutes;

        return dateTime
                .withMinute(bucketMinute)
                .withSecond(0)
                .withNano(0);
    }
}
