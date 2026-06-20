package com.definedge.stock.exceptions;

public class SymbolNotFoundException  extends RuntimeException{
    public SymbolNotFoundException(String symbol) {
        super("No data found for symbol: " + symbol);
    }
}
