package com.example.InterLink.service;

import com.example.InterLink.entity.ApplicationEntity;
import com.example.InterLink.entity.ApplicationStatus;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    List<ApplicationEntity> getAllApplications();
    Optional<ApplicationEntity> getApplicationById(Long id);
    ApplicationEntity saveApplication(ApplicationEntity application);
    ApplicationEntity updateApplication(Long id, ApplicationEntity application);
    ApplicationEntity updateApplicationStatus(Long id, ApplicationStatus status);
    void deleteApplication(Long id);
}
