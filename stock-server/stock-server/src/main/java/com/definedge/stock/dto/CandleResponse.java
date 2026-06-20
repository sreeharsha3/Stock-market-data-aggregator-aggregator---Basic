package com.definedge.stock.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CandleResponse {
    private String symbol;

    private String timeframe;

    private Integer count;

    private List<CandleDto> candles;
}
