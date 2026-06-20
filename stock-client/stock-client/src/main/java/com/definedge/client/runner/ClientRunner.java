package com.definedge.client.runner;

import com.definedge.client.client.CandleApiClient;
import com.definedge.client.dto.CandleDto;
import com.definedge.client.dto.CandleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientRunner  implements CommandLineRunner {
    private final CandleApiClient client;

    @Override
    public void run(String... args) {

        CandleResponse response =
                client.getCandles();

        System.out.println();
        System.out.println("===== RESPONSE =====");
        System.out.println();

        System.out.println("Symbol : "
                + response.getSymbol());

        System.out.println("Timeframe : "
                + response.getTimeframe());

        System.out.println("Count : "
                + response.getCount());

        System.out.println();

        for (CandleDto candle :
                response.getCandles()) {

            System.out.println(candle);
        }
    }
}
