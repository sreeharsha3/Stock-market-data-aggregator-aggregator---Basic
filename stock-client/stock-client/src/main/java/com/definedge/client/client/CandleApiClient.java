package com.definedge.client.client;

import com.definedge.client.dto.CandleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CandleApiClient {
    private final RestTemplate restTemplate;

    public CandleResponse getCandles() {

        String url =
                "http://localhost:8080/api/v1/candles"
                        + "?symbol=TCS"
                        + "&timeframe=5m"
                        + "&start=2026-01-01T09:15:00"
                        + "&end=2026-01-01T09:30:00";

        return restTemplate.getForObject(
                url,
                CandleResponse.class);
    }
}
