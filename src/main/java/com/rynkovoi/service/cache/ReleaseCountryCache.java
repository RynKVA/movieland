package com.rynkovoi.service.cache;

import com.rynkovoi.common.dto.ReleaseCountryDto;
import com.rynkovoi.mapper.CommonMapper;
import com.rynkovoi.repository.ReleaseCountryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReleaseCountryCache implements Cache<ReleaseCountryDto> {

    private final ReleaseCountryRepository releaseCountryRepository;
    private final CommonMapper mapper;
    private volatile Map<Integer, ReleaseCountryDto> cache;

    @Override
    public List<ReleaseCountryDto> findAll() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public boolean isExist(ReleaseCountryDto countryDto) {
        ReleaseCountryDto cachedCountry = cache.get(countryDto.getId());
        return cachedCountry != null && cachedCountry.getName().equals(countryDto.getName());
    }

    @PostConstruct
    @Scheduled(fixedRateString = "${cache.update.interval-hours}",
            initialDelayString = "${cache.delay}",
            timeUnit = TimeUnit.HOURS)
    void updateCache() {
        List<ReleaseCountryDto> countryDtos = mapper.toReleaseCountryDtos(releaseCountryRepository.findAll());
        cache = countryDtos.stream()
                .collect(Collectors.toMap(ReleaseCountryDto::getId, Function.identity()));
        log.info("Cache updated: {} items", cache.size());
    }
}
