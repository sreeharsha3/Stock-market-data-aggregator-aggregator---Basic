package com.definedge.stock.exceptions;

public class InvalidTimeframeException extends RuntimeException {
    public InvalidTimeframeException(String timeframe) {
        super("Unsupported timeframe: " + timeframe);
    }
}
