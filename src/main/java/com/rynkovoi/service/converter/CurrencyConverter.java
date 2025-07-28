package com.rynkovoi.service.converter;

import com.rynkovoi.service.CurrencyRateService;
import com.rynkovoi.type.CurrencyCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CurrencyConverter {

    private final CurrencyRateService repository;

    public BigDecimal convert(BigDecimal value, CurrencyCode currencyCode) {
        BigDecimal currencyRate = repository.getRateByCurrencyCode(currencyCode);
        return value.divide(currencyRate, 1, RoundingMode.HALF_UP);
    }
}
