package com.rynkovoi.service.converter;

import com.rynkovoi.repository.CurrencyRateRepository;
import com.rynkovoi.type.CurrencyCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CurrencyConverter {

    private final CurrencyRateRepository repository;

    public BigDecimal convert(BigDecimal value, CurrencyCode currencyCode) {
        if (currencyCode.equals(CurrencyCode.UAH)) {
            return value;
        }
        BigDecimal currencyRate = repository.getRateByCurrencyCode(currencyCode);
        return value.divide(currencyRate, 1, RoundingMode.HALF_UP);
    }
}
