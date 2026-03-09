package com.talentotech.redcomunitaria.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.talentotech.redcomunitaria.exception.ResourceNotFoundException;
import com.talentotech.redcomunitaria.model.Person;
import com.talentotech.redcomunitaria.service.PersonService;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.create(person));
    }

    @GetMapping
    public List<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable Long id) {
        return personService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID: " + id));
    }

    @PutMapping("/{id}")
    public Person update(@PathVariable Long id, @RequestBody Person personDetails) {
        return personService.update(id, personDetails);
    }
}