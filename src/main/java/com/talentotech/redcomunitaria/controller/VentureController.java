package com.talentotech.redcomunitaria.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.talentotech.redcomunitaria.exception.ResourceNotFoundException;
import com.talentotech.redcomunitaria.model.Venture;
import com.talentotech.redcomunitaria.service.VentureService;

@RestController
@RequestMapping("/api/ventures")
public class VentureController {

    private final VentureService ventureService;

    public VentureController(VentureService ventureService) {
        this.ventureService = ventureService;
    }

    @PostMapping
    public ResponseEntity<Venture> create(@RequestBody Venture venture) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventureService.create(venture));
    }

    @GetMapping
    public List<Venture> findAll() {
        return ventureService.findAll();
    }

    @GetMapping("/{id}")
    public Venture findById(@PathVariable Long id) {
        return ventureService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Emprendimiento no encontrado con ID: " + id));
    }

    @PutMapping("/{id}")
    public Venture update(@PathVariable Long id, @RequestBody Venture ventureDetails) {
        return ventureService.update(id, ventureDetails);
    }

    @GetMapping("/status/{status}")
    public List<Venture> findByStatus(@PathVariable String status) {
        return ventureService.findByStatus(status);
    }

    @GetMapping("/type/{ventureType}")
    public List<Venture> findByVentureType(@PathVariable String ventureType) {
        return ventureService.findByVentureType(ventureType);
    }
}