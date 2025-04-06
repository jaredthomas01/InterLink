package com.example.InterLink.controller;


import com.example.InterLink.entity.ApplicationEntity;
import com.example.InterLink.entity.ApplicationStatus;
import com.example.InterLink.entity.PlacementEntity;
import com.example.InterLink.entity.StudentEntity;
import com.example.InterLink.service.ApplicationService;
import com.example.InterLink.service.PlacementService;
import com.example.InterLink.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
    @RequestMapping("/applications")
    public class ApplicationController {

    private final ApplicationService applicationService;
    private final StudentService studentService;
    private final PlacementService placementService;

    @Autowired
    public ApplicationController(
            ApplicationService applicationService,
            StudentService studentService,
            PlacementService placementService) {
        this.applicationService = applicationService;
        this.studentService = studentService;
        this.placementService = placementService;
    }

    @GetMapping
    public ResponseEntity<List<ApplicationEntity>> getAllApplications() {
        List<ApplicationEntity> applications = applicationService.getAllApplications();
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createApplication(
            @RequestParam Long studentId,
            @RequestParam Long placementId,
            @RequestParam String course,
            @RequestParam String university,
            @RequestParam String phone,
            @RequestParam String coverLetter,
            @RequestParam MultipartFile resume) {
        try {
            Optional<StudentEntity> studentOpt = studentService.getStudentById(studentId);
            Optional<PlacementEntity> placementOpt = placementService.getPlacementById(placementId);

            if (studentOpt.isEmpty() || placementOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student or Placement not found.");
            }

            ApplicationEntity application = new ApplicationEntity();
            application.setStudent(studentOpt.get());
            application.setPlacement(placementOpt.get());
            application.setCourse(course);
            application.setUniversity(university);
            application.setPhone(phone);
            application.setCoverLetter(coverLetter);
            application.setResumeFile(resume.getBytes());
            application.setResumeFileName(resume.getOriginalFilename());
            application.setStatus(ApplicationStatus.PENDING); // ✅ Default status

            ApplicationEntity saved = applicationService.saveApplication(application);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to read resume file.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
        @GetMapping("/{id}")
        public ResponseEntity<ApplicationEntity> getApplicationById(@PathVariable Long id) {
            Optional<ApplicationEntity> application = applicationService.getApplicationById(id);
            return application.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @PatchMapping("/{id}/status")
        public ResponseEntity<ApplicationEntity> updateApplicationStatus(@PathVariable Long id, @RequestParam ApplicationStatus status) {
            ApplicationEntity updatedApplication = applicationService.updateApplicationStatus(id, status);
            return updatedApplication != null ? new ResponseEntity<>(updatedApplication, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

