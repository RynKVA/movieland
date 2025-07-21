package com.rynkovoi.service.converter;

import com.rynkovoi.repository.CurrencyRateRepository;
import com.rynkovoi.type.CurrencyCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension .class)
class CurrencyConverterTest {

    @Mock
    private CurrencyRateRepository repository;

    @InjectMocks
    private CurrencyConverter currencyConverter;

    @Test
    void testConvertWithCurrencyCodeUSD() {
        BigDecimal value = BigDecimal.valueOf(100);
        CurrencyCode currencyCode = CurrencyCode.USD;
        BigDecimal rate = BigDecimal.valueOf(27.5);

        when(repository.getRateByCurrencyCode(currencyCode)).thenReturn(rate);

        BigDecimal expected = value.divide(rate, 1, RoundingMode.HALF_UP);
        BigDecimal result = currencyConverter.convert(value, currencyCode);

        assertEquals(expected, result);
    }
}