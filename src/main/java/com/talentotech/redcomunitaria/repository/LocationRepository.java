package com.talentotech.redcomunitaria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talentotech.redcomunitaria.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByCountryAndRegionAndCity(String country, String region, String city);

    boolean existsByCountryAndRegionAndCity(String country, String region, String city);
}