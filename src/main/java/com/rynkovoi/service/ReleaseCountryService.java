package com.rynkovoi.service;

import com.rynkovoi.model.ReleaseCountry;

import java.util.List;
import java.util.Set;

public interface ReleaseCountryService {

    Set<ReleaseCountry> getAllByIds(List<Integer> ids);
}
