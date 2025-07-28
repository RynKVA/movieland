package com.rynkovoi.service;

import com.rynkovoi.type.CurrencyCode;

import java.math.BigDecimal;

public interface CurrencyRateService {

    BigDecimal getRateByCurrencyCode(CurrencyCode currencyCode);
}
