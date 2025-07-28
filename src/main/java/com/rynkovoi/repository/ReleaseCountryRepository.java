package com.rynkovoi.repository;

import com.rynkovoi.model.ReleaseCountry;

import java.util.List;
import java.util.Set;

public interface ReleaseCountryRepository {

    List<ReleaseCountry> findAll();

    Set<ReleaseCountry> findByIdIn(List<Integer> ids);
}
