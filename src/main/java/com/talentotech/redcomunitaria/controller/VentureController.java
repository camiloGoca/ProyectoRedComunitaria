package com.talentotech.redcomunitaria.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.talentotech.redcomunitaria.exception.ResourceNotFoundException;
import com.talentotech.redcomunitaria.model.Venture;
import com.talentotech.redcomunitaria.service.VentureService;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")

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
    public ResponseEntity<Venture> findById(@PathVariable Long id) {
        Venture venture = ventureService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Emprendimiento no encontrado con ID: " + id));
        return ResponseEntity.ok(venture);
    }

    //con este endpoint exponemos la consulta  de produccion total
    @GetMapping("/statistics/production")
     public ResponseEntity<List<Object[]>> getTotalProductionByRegionAndVentureType() {
    List<Object[]> productionStats = ventureService.getTotalProductionByRegionAndVentureType();
        return ResponseEntity.ok(productionStats);
     }

    @GetMapping("/statistics/venture-percentage")
     public ResponseEntity<List<Object[]>> getVenturePercentageByRegion() {
      List<Object[]> ventureStats = ventureService.getVenturePercentageByRegion();
        return ResponseEntity.ok(ventureStats);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venture> update(@PathVariable Long id, @RequestBody Venture ventureDetails) {
        Venture updatedVenture = ventureService.update(id, ventureDetails);
        return ResponseEntity.ok(updatedVenture);
    }

    @GetMapping("/filter")
    public List<Venture> filterVentures(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String ventureType,
            @RequestParam(required = false) String region) {
        return ventureService.filterVentures(status, ventureType, region);
    }

    @GetMapping("/statistics/top-countries")
    public ResponseEntity<List<Object[]>> getTopCountriesByVentureCount() {
        List<Object[]> topCountries = ventureService.getTopCountriesByVentureCount();
        return ResponseEntity.ok(topCountries);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
         ventureService.delete(id);
         return ResponseEntity.noContent().build();
    }
}