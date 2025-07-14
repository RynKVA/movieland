package com.rynkovoi.repository.cache;

import com.rynkovoi.repository.CurrencyRateRepository;
import com.rynkovoi.type.CurrencyCode;
import com.rynkovoi.common.dto.NbuCurrencyRate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class CachedCurrencyRateRepository extends AbstractCachedRepository<NbuCurrencyRate> implements CurrencyRateRepository {

    private static final String NBU_API_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    protected List<NbuCurrencyRate> fillCache() {
        return getRatesFromNbu();
    }

    @Override
    public BigDecimal getRateByCurrencyCode(CurrencyCode currencyCode) {
        return findAllDto().stream()
                .filter(rate -> rate.getCurrencyCode().equalsIgnoreCase(currencyCode.name()))
                .map(NbuCurrencyRate::getCurrencyRate)
                .findFirst()
                .orElse(BigDecimal.ONE);
    }

    private List<NbuCurrencyRate> getRatesFromNbu() {
        ResponseEntity<List<NbuCurrencyRate>> response = restTemplate.exchange(
                NBU_API_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }
}
