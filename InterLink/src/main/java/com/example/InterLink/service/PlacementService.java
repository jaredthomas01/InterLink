package com.example.InterLink.service;

import com.example.InterLink.entity.PlacementEntity;

import java.util.List;
import java.util.Optional;

public interface PlacementService {
    List<PlacementEntity> getAllPlacements();
    Optional<PlacementEntity> getPlacementById(Long id);
    PlacementEntity savePlacement(PlacementEntity placementEntity);
    PlacementEntity updatePlacement(Long id, PlacementEntity placementEntity);
    void deletePlacement(Long id);
}