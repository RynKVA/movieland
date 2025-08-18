package com.rynkovoi.service.converter;

import com.rynkovoi.service.CurrencyRateService;
import com.rynkovoi.type.CurrencyCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyConverter {

    private final CurrencyRateService repository;

    public BigDecimal convert(BigDecimal value, CurrencyCode currencyCode) {
        log.info("Converting value: {} to currency: {}", value, currencyCode.name());
        BigDecimal currencyRate = repository.getRateByCurrencyCode(currencyCode);
        return value.divide(currencyRate, 1, RoundingMode.HALF_UP);
    }
}
