package com.rynkovoi.repository;

import com.rynkovoi.type.CurrencyCode;

import java.math.BigDecimal;

public interface CurrencyRateRepository {

    BigDecimal getRateByCurrencyCode(CurrencyCode currencyCode);
}
