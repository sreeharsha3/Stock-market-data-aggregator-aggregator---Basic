package com.definedge.stock.enums;

import com.definedge.stock.exceptions.InvalidTimeframeException;

import java.util.Arrays;

public enum TimeFrame {
    M1("1m",1),
    M5("5m",5),
    M15("15m",15),
    M30("30m",30),
    H1("1h",60),
    D1("1d",1440);

    private final String code;
    private final int minutes;

    TimeFrame(String code,int minutes) {
        this.code = code;
        this.minutes = minutes;
    }

    public String getCode() {
        return code;
    }

    public int getMinutes() {
        return minutes;
    }

    public static TimeFrame from(String value) {

        return Arrays.stream(values())
                .filter(t ->
                        t.code.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(
                        () ->
                                new InvalidTimeframeException(value));
    }
}
