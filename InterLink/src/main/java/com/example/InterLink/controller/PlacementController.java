package com.example.InterLink.controller;

import com.example.InterLink.entity.PlacementEntity;
import com.example.InterLink.service.PlacementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/placements")
public class PlacementController {

    private final PlacementService placementService;

    @Autowired
    public PlacementController(PlacementService placementService) {
        this.placementService = placementService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlacementEntity> getPlacementById(@PathVariable Long id) {
        Optional<PlacementEntity> placement = placementService.getPlacementById(id);
        return placement.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/placements")
    public ResponseEntity<PlacementEntity> createPlacement(@RequestBody PlacementEntity placement) {
        PlacementEntity savedPlacement = placementService.savePlacement(placement);
        return new ResponseEntity<>(savedPlacement, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PlacementEntity>> getAllPlacements() {
        return new ResponseEntity<>(placementService.getAllPlacements(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlacementEntity> updatePlacement(@PathVariable Long id, @RequestBody PlacementEntity placementEntity) {
        PlacementEntity updatedPlacement = placementService.updatePlacement(id, placementEntity);
        return updatedPlacement != null ? new ResponseEntity<>(updatedPlacement, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlacement(@PathVariable Long id) {
        placementService.deletePlacement(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}