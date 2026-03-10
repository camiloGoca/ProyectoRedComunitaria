package com.talentotech.redcomunitaria.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.talentotech.redcomunitaria.exception.ResourceNotFoundException;
import com.talentotech.redcomunitaria.model.Venture;
import com.talentotech.redcomunitaria.model.Location;
import com.talentotech.redcomunitaria.model.Person;
import com.talentotech.redcomunitaria.model.User;
import com.talentotech.redcomunitaria.repository.VentureRepository;
import com.talentotech.redcomunitaria.repository.PersonRepository;
import com.talentotech.redcomunitaria.repository.LocationRepository;
import com.talentotech.redcomunitaria.repository.UserRepository;

@Service
public class VentureService {

    private final VentureRepository ventureRepository;
    private final PersonRepository personRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    public VentureService(VentureRepository ventureRepository,
                          PersonRepository personRepository,
                          LocationRepository locationRepository,
                          UserRepository userRepository) {
        this.ventureRepository = ventureRepository;
        this.personRepository = personRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    public Venture create(Venture venture) {
        Long personId = venture.getPerson().getId();
        Long locationId = venture.getLocation().getId();
        Long userId = venture.getUser().getId();

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada"));

        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        venture.setPerson(person);
        venture.setLocation(location);
        venture.setUser(user);

        return ventureRepository.save(venture);
    }

    public List<Venture> filterVentures(String status, String ventureType, String region) {
        if (status != null && ventureType != null && region != null) {
            return ventureRepository.findByStatusAndVentureTypeAndLocation_Region(status, ventureType, region);
        } else if (status != null && ventureType != null) {
            return ventureRepository.findByStatusAndVentureType(status, ventureType);
        } else if (status != null && region != null) {
            return ventureRepository.findByStatusAndLocation_Region(status, region); 
        } else if (ventureType != null && region != null) {
            return ventureRepository.findByVentureTypeAndLocation_Region(ventureType, region);
        } else if (status != null) {
            return ventureRepository.findByStatus(status);
        } else if (ventureType != null) {
            return ventureRepository.findByVentureType(ventureType);
        } else if (region != null) {
            return ventureRepository.findByLocation_Region(region);
        } else {
            return ventureRepository.findAll();
        }
    }

    public List<Venture> findAll() {
        return ventureRepository.findAll();
    }

    public Optional<Venture> findById(Long id) {
        return ventureRepository.findById(id);
    }

    public Venture update(Long id, Venture ventureDetails) {
        Venture venture = ventureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Emprendimiento no encontrado"));

        if (ventureDetails.getName() != null && !ventureDetails.getName().trim().isEmpty()) {
            venture.setName(ventureDetails.getName());
        }

        if (ventureDetails.getVentureType() != null && !ventureDetails.getVentureType().trim().isEmpty()) {
            venture.setVentureType(ventureDetails.getVentureType());
        }

        if (ventureDetails.getSector() != null) {
            venture.setSector(ventureDetails.getSector());
        }

        if (ventureDetails.getStartDate() != null) {
            venture.setStartDate(ventureDetails.getStartDate());
        }

        if (ventureDetails.getStatus() != null) {
            venture.setStatus(ventureDetails.getStatus());
        }

        if (ventureDetails.getEmployees() != null) {
            venture.setEmployees(ventureDetails.getEmployees());
        }

        if (ventureDetails.getMonthlyIncome() != null) {
            venture.setMonthlyIncome(ventureDetails.getMonthlyIncome());
        }

        if (ventureDetails.getTotalProduction() != null) {
            venture.setTotalProduction(ventureDetails.getTotalProduction());
        }

        if (ventureDetails.getPerson() != null && ventureDetails.getPerson().getId() != null) {
            Person person = personRepository.findById(ventureDetails.getPerson().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada"));
            venture.setPerson(person);
        }

        if (ventureDetails.getLocation() != null && ventureDetails.getLocation().getId() != null) {
            Location location = locationRepository.findById(ventureDetails.getLocation().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada"));
            venture.setLocation(location);
        }

        if (ventureDetails.getUser() != null && ventureDetails.getUser().getId() != null) {
            User user = userRepository.findById(ventureDetails.getUser().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            venture.setUser(user);
        }

        return ventureRepository.save(venture);
    }

    public List<Object[]> getTotalProductionByRegionAndVentureType() {
        return ventureRepository.findTotalProductionByRegionAndVentureType();
     }

    public void delete(Long id) {
        Venture venture = ventureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Emprendimiento no encontrado con ID: " + id));
        ventureRepository.delete(venture);
    }
}