package com.example.InterLink.service.impl;

import com.example.InterLink.entity.ApplicationEntity;
import com.example.InterLink.repository.ApplicationRepository;
import com.example.InterLink.service.ApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public List<ApplicationEntity> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public Optional<ApplicationEntity> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    @Override
    public ApplicationEntity saveApplication(ApplicationEntity applicationEntity) {
        return applicationRepository.save(applicationEntity);
    }

    @Override
    public ApplicationEntity updateApplication(Long id, ApplicationEntity applicationEntity) {
        return applicationRepository.findById(id).map(existingApp -> {
            existingApp.setStudent(applicationEntity.getStudent());
            existingApp.setPlacement(applicationEntity.getPlacement());
            return applicationRepository.save(existingApp);
        }).orElse(null);
    }

    @Override
    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }
}