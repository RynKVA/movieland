package com.rynkovoi.service.impl;

import com.rynkovoi.common.dto.NbuCurrencyRate;
import com.rynkovoi.service.CurrencyRateService;
import com.rynkovoi.type.CurrencyCode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class CachedCurrencyRateService implements CurrencyRateService {

    private final RestClient restClient;
    private final Map<String, NbuCurrencyRate> cache = new ConcurrentHashMap<>();
    private final static DateTimeFormatter DATA_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public BigDecimal getRateByCurrencyCode(CurrencyCode currencyCode) {
        return cache.get(currencyCode.name()).getCurrencyRate();
    }

    @PostConstruct
    @Scheduled(cron = "0 0 0 * * ?")
    void updateCache() {
        List<NbuCurrencyRate> rates = getRatesFromNbu();
        for (NbuCurrencyRate rate : rates) {
            cache.put(rate.getCurrencyCode(), rate);
        }
        log.info("Currency cache updated: {}", cache.size());
    }

    private List<NbuCurrencyRate> getRatesFromNbu() {
        ResponseEntity<List<NbuCurrencyRate>> response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("date", LocalDate.now().format(DATA_FORMAT))
                        .queryParam("json", "")
                        .build())
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }
}
