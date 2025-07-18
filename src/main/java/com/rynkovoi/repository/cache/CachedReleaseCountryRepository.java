package com.rynkovoi.repository.cache;

import com.rynkovoi.common.dto.ReleaseCountryDto;
import com.rynkovoi.mapper.CommonMapper;
import com.rynkovoi.repository.ReleaseCountryRepository;
import com.rynkovoi.repository.jpa.JpaReleaseCountryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CachedReleaseCountryRepository implements ReleaseCountryRepository {

    private final JpaReleaseCountryRepository releaseCountryRepository;
    private final CommonMapper mapper;
    private volatile List<ReleaseCountryDto> cache;


    @Override
    public List<ReleaseCountryDto> findAllDto() {
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
