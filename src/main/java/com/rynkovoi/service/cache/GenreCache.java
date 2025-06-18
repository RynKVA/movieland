package com.rynkovoi.service.cache;

import java.util.List;

public interface GenreCache<T> {

    void refill(List<T> values);

    List<T> getValues();
}
