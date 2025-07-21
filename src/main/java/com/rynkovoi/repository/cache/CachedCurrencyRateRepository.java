package com.rynkovoi.repository.cache;

import com.rynkovoi.common.dto.NbuCurrencyRate;
import com.rynkovoi.repository.CurrencyRateRepository;
import com.rynkovoi.type.CurrencyCode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CachedCurrencyRateRepository implements CurrencyRateRepository {

    private final RestClient restClient;
    private final Map<String, NbuCurrencyRate> cache = new ConcurrentHashMap<>();

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
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }
}
