package com.talentotech.redcomunitaria.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.talentotech.redcomunitaria.exception.ResourceNotFoundException;
import com.talentotech.redcomunitaria.model.Innovation;
import com.talentotech.redcomunitaria.service.InnovationService;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api/innovations")
public class InnovationController {

    private final InnovationService innovationService;

    public InnovationController(InnovationService innovationService) {
        this.innovationService = innovationService;
    }

    @PostMapping
    public ResponseEntity<Innovation> create(@RequestBody Innovation innovation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(innovationService.create(innovation));
    }

    @GetMapping
    public List<Innovation> findAll() {
        return innovationService.findAll();
    }

    @GetMapping("/{id}")
    public Innovation findById(@PathVariable Long id) {
        return innovationService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Innovación no encontrada con ID: " + id));
    }

    @PutMapping("/{id}")
    public Innovation update(@PathVariable Long id, @RequestBody Innovation innovationDetails) {
        return innovationService.update(id, innovationDetails);
    }

    @GetMapping("/type/{innovationType}")
    public List<Innovation> findByInnovationType(@PathVariable String innovationType) {
        return innovationService.findByInnovationType(innovationType);
    }

    @GetMapping("/level/{innovationLevel}")
    public List<Innovation> findByInnovationLevel(@PathVariable String innovationLevel) {
        return innovationService.findByInnovationLevel(innovationLevel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
         innovationService.delete(id);
         return ResponseEntity.noContent().build();
    }
}