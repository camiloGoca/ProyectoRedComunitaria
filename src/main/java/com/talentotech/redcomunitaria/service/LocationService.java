package com.talentotech.redcomunitaria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.talentotech.redcomunitaria.exception.ResourceNotFoundException;
import com.talentotech.redcomunitaria.model.Location;
import com.talentotech.redcomunitaria.repository.LocationRepository;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location create(Location location) {
        boolean exists = locationRepository.existsByCountryAndRegionAndCity(
                location.getCountry(),
                location.getRegion(),
                location.getCity()
        );

        if (exists) {
            throw new IllegalArgumentException("La ubicación ya existe");
        }

        return locationRepository.save(location);
    }

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public Optional<Location> findById(Long id) {
        return locationRepository.findById(id);
    }

    public Location update(Long id, Location locationDetails) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada"));

        if (locationDetails.getCountry() != null && !locationDetails.getCountry().trim().isEmpty()) {
            location.setCountry(locationDetails.getCountry());
        }

        if (locationDetails.getRegion() != null && !locationDetails.getRegion().trim().isEmpty()) {
            location.setRegion(locationDetails.getRegion());
        }

        location.setCity(locationDetails.getCity());

        return locationRepository.save(location);
    }
}