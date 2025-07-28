package com.rynkovoi.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class NbuCurrencyRate {

    @JsonProperty("cc")
    private final String currencyCode;
    @JsonProperty("rate")
    private final BigDecimal currencyRate;
}
