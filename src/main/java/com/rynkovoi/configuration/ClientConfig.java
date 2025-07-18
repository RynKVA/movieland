package com.rynkovoi.configuration;

import com.rynkovoi.properties.CurrencyProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class ClientConfig {

    private final CurrencyProperty currencyProperty;

    @Bean
    public RestClient nbuApiClient(RestClient.Builder builder) {
        return builder
                .baseUrl(currencyProperty.getNbuExchangeUrl())
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
