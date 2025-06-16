/*
package com.rynkovoi.service.cache;

import generated.tables.records.GenresRecord;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
class GenreCacheTest {

    @Test
    void testConcurrentSavesAndClear() throws InterruptedException {
        DefaultGenreCache cache = new DefaultGenreCache();

        int saverThreads = 20;
        int clearerThreads = 5;
        int totalThreads = saverThreads + clearerThreads;

        ExecutorService executor = Executors.newFixedThreadPool(totalThreads);
        CountDownLatch latch = new CountDownLatch(totalThreads);

        AtomicInteger totalSaves = new AtomicInteger(0);
        AtomicInteger clearsDone = new AtomicInteger(0);

        //saver threads
        for (int i = 0; i < saverThreads; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < 15; j++) {
                        log.info("Saver thread {}: saving in cache", Thread.currentThread().getName());
                        GenresRecord genre = new GenresRecord();
                        genre.setName("Genre " + j);
                        cache.save(List.of(genre));
                        totalSaves.incrementAndGet();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        // clearer threads
        for (int i = 0; i < clearerThreads; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < 2; j++) {
                        log.info("Clearer thread {}: clearing cache", Thread.currentThread().getName());
                        cache.clear();
                        clearsDone.incrementAndGet();
                        Thread.sleep(10);
                    }
                } catch (InterruptedException ignored) {
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executor.shutdown();

        log.info("Total saves: {}", totalSaves.get());
        assertEquals(300, totalSaves.get());
        log.info("Total clears: {}", clearsDone.get());
        assertEquals(10, clearsDone.get());

        List<GenresRecord> genres = cache.getValues();
        log.info("Quantity of genres in cache: {}", genres.size());
        for (GenresRecord genre : genres) {
            assertNotNull(genre);
            assertNotNull(genre.getName());
        }
    }
}*/
