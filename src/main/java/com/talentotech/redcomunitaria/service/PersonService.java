package com.talentotech.redcomunitaria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.talentotech.redcomunitaria.exception.ResourceNotFoundException;
import com.talentotech.redcomunitaria.model.Location;
import com.talentotech.redcomunitaria.model.Person;
import com.talentotech.redcomunitaria.model.User;
import com.talentotech.redcomunitaria.repository.LocationRepository;
import com.talentotech.redcomunitaria.repository.PersonRepository;
import com.talentotech.redcomunitaria.repository.UserRepository;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    public PersonService(PersonRepository personRepository,
                         LocationRepository locationRepository,
                         UserRepository userRepository) {
        this.personRepository = personRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    public Person create(Person person) {
        Long locationId = person.getLocation().getId();
        Long userId = person.getUser().getId();

        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        person.setLocation(location);
        person.setUser(user);

        return personRepository.save(person);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    public Person update(Long id, Person personDetails) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada"));

        if (personDetails.getFirstName() != null && !personDetails.getFirstName().trim().isEmpty()) {
            person.setFirstName(personDetails.getFirstName());
        }

        if (personDetails.getLastName() != null && !personDetails.getLastName().trim().isEmpty()) {
            person.setLastName(personDetails.getLastName());
        }

        if (personDetails.getBirthDate() != null) {
            person.setBirthDate(personDetails.getBirthDate());
        }

        if (personDetails.getGender() != null) {
            person.setGender(personDetails.getGender());
        }

        if (personDetails.getEducationLevel() != null) {
            person.setEducationLevel(personDetails.getEducationLevel());
        }

        if (personDetails.getLocation() != null && personDetails.getLocation().getId() != null) {
            Location location = locationRepository.findById(personDetails.getLocation().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada"));
            person.setLocation(location);
        }

        if (personDetails.getUser() != null && personDetails.getUser().getId() != null) {
            User user = userRepository.findById(personDetails.getUser().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            person.setUser(user);
        }

        return personRepository.save(person);
    }
}