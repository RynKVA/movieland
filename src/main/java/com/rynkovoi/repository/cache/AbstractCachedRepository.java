package com.rynkovoi.repository.cache;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class AbstractCachedRepository<T> {

    private volatile List<T> cache;

    public List<T> findAllDto() {
        return new ArrayList<>(cache);
    }

    protected abstract List<T> fillCache();

    @PostConstruct
    @Scheduled(fixedRateString = "${cache.update.interval-hours}",
            initialDelayString = "${cache.delay.interval-hours}",
            timeUnit = TimeUnit.HOURS)
    public void updateCache() {
        cache = fillCache();
        if (cache.isEmpty()){
            log.warn("Cache is empty after update");
            return;
        }
        log.info("Cache updated: {} items of type {}", cache.size(), cache.getFirst().getClass().getSimpleName());
    }
}
