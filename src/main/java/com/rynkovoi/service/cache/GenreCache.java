package com.rynkovoi.service.cache;

import java.util.List;

public interface GenreCache<T> {

    void refill(List<T> values);

    int size();

    boolean isEmpty();

    List<T> getValues();

}
