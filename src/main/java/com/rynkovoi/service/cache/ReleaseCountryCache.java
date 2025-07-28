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
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReleaseCountryCache implements Cache<ReleaseCountryDto>{

    private final ReleaseCountryRepository releaseCountryRepository;
    private final CommonMapper mapper;
    private volatile List<ReleaseCountryDto> cache;

    @Override
    public List<ReleaseCountryDto> findAll() {
        return new ArrayList<>(cache);
    }

    @PostConstruct
    @Scheduled(fixedRateString = "${cache.update.interval-hours}",
            initialDelayString = "${cache.delay}",
            timeUnit = TimeUnit.HOURS)
    void updateCache() {
        cache = mapper.toReleaseCountryDtos(releaseCountryRepository.findAll());
        log.info("Cache updated: {} items", cache.size());
    }
}
