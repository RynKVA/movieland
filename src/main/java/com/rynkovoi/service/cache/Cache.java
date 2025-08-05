package com.rynkovoi.service.cache;

import java.util.List;

public interface Cache <T>{

    List<T> findAll();

    boolean isExist(T dto);
}
