package com.example.InterLink.service.impl;

import com.example.InterLink.entity.PlacementEntity;
import com.example.InterLink.repository.PlacementRepository;
import com.example.InterLink.service.PlacementService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlacementServiceImpl implements PlacementService {

    private final PlacementRepository placementRepository;

    public PlacementServiceImpl(PlacementRepository placementRepository) {
        this.placementRepository = placementRepository;
    }

    @Override
    public List<PlacementEntity> getAllPlacements() {
        return placementRepository.findAll();
    }

    @Override
    public Optional<PlacementEntity> getPlacementById(Long id) {
        return placementRepository.findById(id);
    }

    @Override
    public PlacementEntity savePlacement(PlacementEntity placementEntity) {
        return placementRepository.save(placementEntity);
    }

    @Override
    public PlacementEntity updatePlacement(Long id, PlacementEntity placementEntity) {
        return placementRepository.findById(id).map(existingPlacement -> {
            existingPlacement.setTitle(placementEntity.getTitle());
            existingPlacement.setDescription(placementEntity.getDescription());
            existingPlacement.setRequirements(placementEntity.getRequirements());
            return placementRepository.save(existingPlacement);
        }).orElse(null);
    }

    @Override
    public void deletePlacement(Long id) {
        placementRepository.deleteById(id);
    }
}