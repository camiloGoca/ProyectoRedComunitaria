package com.talentotech.redcomunitaria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.talentotech.redcomunitaria.exception.ResourceNotFoundException;
import com.talentotech.redcomunitaria.model.Innovation;
import com.talentotech.redcomunitaria.model.Location;
import com.talentotech.redcomunitaria.model.Person;
import com.talentotech.redcomunitaria.model.User;
import com.talentotech.redcomunitaria.repository.InnovationRepository;
import com.talentotech.redcomunitaria.repository.LocationRepository;
import com.talentotech.redcomunitaria.repository.PersonRepository;
import com.talentotech.redcomunitaria.repository.UserRepository;

@Service
public class InnovationService {

    private final InnovationRepository innovationRepository;
    private final PersonRepository personRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    public InnovationService(InnovationRepository innovationRepository,
                             PersonRepository personRepository,
                             LocationRepository locationRepository,
                             UserRepository userRepository) {
        this.innovationRepository = innovationRepository;
        this.personRepository = personRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    public Innovation create(Innovation innovation) {
        Long personId = innovation.getPerson().getId();
        Long locationId = innovation.getLocation().getId();
        Long userId = innovation.getUser().getId();

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada"));

        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        innovation.setPerson(person);
        innovation.setLocation(location);
        innovation.setUser(user);

        return innovationRepository.save(innovation);
    }

    public List<Innovation> findAll() {
        return innovationRepository.findAll();
    }

    public Optional<Innovation> findById(Long id) {
        return innovationRepository.findById(id);
    }

    public Innovation update(Long id, Innovation innovationDetails) {
        Innovation innovation = innovationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Innovación no encontrada"));

        if (innovationDetails.getName() != null && !innovationDetails.getName().trim().isEmpty()) {
            innovation.setName(innovationDetails.getName());
        }

        if (innovationDetails.getInnovationType() != null && !innovationDetails.getInnovationType().trim().isEmpty()) {
            innovation.setInnovationType(innovationDetails.getInnovationType());
        }

        if (innovationDetails.getInnovationLevel() != null && !innovationDetails.getInnovationLevel().trim().isEmpty()) {
            innovation.setInnovationLevel(innovationDetails.getInnovationLevel());
        }

        if (innovationDetails.getInnovationDate() != null) {
            innovation.setInnovationDate(innovationDetails.getInnovationDate());
        }

        if (innovationDetails.getImpactScore() != null) {
            innovation.setImpactScore(innovationDetails.getImpactScore());
        }

        if (innovationDetails.getDescription() != null) {
            innovation.setDescription(innovationDetails.getDescription());
        }

        if (innovationDetails.getPerson() != null && innovationDetails.getPerson().getId() != null) {
            Person person = personRepository.findById(innovationDetails.getPerson().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada"));
            innovation.setPerson(person);
        }

        if (innovationDetails.getLocation() != null && innovationDetails.getLocation().getId() != null) {
            Location location = locationRepository.findById(innovationDetails.getLocation().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada"));
            innovation.setLocation(location);
        }

        if (innovationDetails.getUser() != null && innovationDetails.getUser().getId() != null) {
            User user = userRepository.findById(innovationDetails.getUser().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            innovation.setUser(user);
        }

        return innovationRepository.save(innovation);
    }

    public List<Innovation> findByInnovationType(String innovationType) {
        return innovationRepository.findByInnovationType(innovationType);
    }

    public List<Innovation> findByInnovationLevel(String innovationLevel) {
        return innovationRepository.findByInnovationLevel(innovationLevel);
    }
}