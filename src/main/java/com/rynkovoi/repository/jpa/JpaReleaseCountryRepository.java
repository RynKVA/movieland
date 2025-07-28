package com.rynkovoi.repository.jpa;

import com.rynkovoi.model.ReleaseCountry;
import com.rynkovoi.repository.ReleaseCountryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("jpa")
public interface JpaReleaseCountryRepository extends JpaRepository<ReleaseCountry, Integer>, ReleaseCountryRepository {
}
