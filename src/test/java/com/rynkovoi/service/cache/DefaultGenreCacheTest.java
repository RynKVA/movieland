package com.rynkovoi.service.cache;

import com.rynkovoi.ExemplarsCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class DefaultGenreCacheTest {
//
//    private DefaultGenreCache cache;
//
//    @Test
//    void refill() {
//        cache = new DefaultGenreCache();
//
//        cache.refill(ExemplarsCreator.createGenreListWithThreeGenres());
//
//        assertEquals(3, cache.getValues().size());
//        assertFalse(cache.getValues().isEmpty());
//    }
//
//    @Test
//    void getValues() {
//        cache = new DefaultGenreCache();
//        cache.refill(ExemplarsCreator.createGenreListWithThreeGenres());
//
//        assertEquals(3, cache.getValues().size());
//        assertEquals("Action", cache.getValues().get(0).getName());
//        assertEquals("Comedy", cache.getValues().get(1).getName());
//        assertEquals("Drama", cache.getValues().get(2).getName());
//    }
//}