package com.example.InterLink.service.impl;

import com.example.InterLink.entity.ApplicationEntity;
import com.example.InterLink.entity.ApplicationStatus;
import com.example.InterLink.repository.ApplicationRepository;
import com.example.InterLink.service.ApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public boolean applicationExists(Long studentId, Long placementId) {
        return applicationRepository.existsByStudentIdAndPlacementId(studentId, placementId);
    }



    @Override
    public ApplicationEntity saveApplication(ApplicationEntity applicationEntity) {
        boolean alreadyExists = applicationRepository.existsByStudentIdAndPlacementId(
                applicationEntity.getStudent().getId(),
                applicationEntity.getPlacement().getId()
        );

        if (alreadyExists) {
            throw new IllegalStateException("You have already applied for this job.");
        }

        return applicationRepository.save(applicationEntity);
    }

    public ApplicationEntity saveApplicationWithDetails(ApplicationEntity applicationEntity,
                                                        String fullName,
                                                        String regNo,
                                                        String course,
                                                        String university,
                                                        String phone,
                                                        String coverLetter,
                                                        MultipartFile resumeFile) throws IOException {

        if (applicationRepository.existsByStudentIdAndPlacementId(
                applicationEntity.getStudent().getId(),
                applicationEntity.getPlacement().getId())) {
            throw new IllegalStateException("You have already applied for this job.");
        }

        applicationEntity.setFullName(fullName);
        applicationEntity.setRegNo(regNo);
        applicationEntity.setCourse(course);
        applicationEntity.setUniversity(university);
        applicationEntity.setPhone(phone);
        applicationEntity.setCoverLetter(coverLetter);

        if (resumeFile != null && !resumeFile.isEmpty()) {
            applicationEntity.setResumeFile(resumeFile.getBytes());
        }

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
    public ApplicationEntity updateApplicationStatus(Long id, ApplicationStatus status) {
        return applicationRepository.findById(id).map(existingApp -> {
            existingApp.setStatus(status);
            return applicationRepository.save(existingApp);
        }).orElse(null);
    }

    @Override
    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }
}