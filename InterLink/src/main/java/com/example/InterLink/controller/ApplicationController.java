package com.example.InterLink.controller;


import com.example.InterLink.entity.ApplicationEntity;
import com.example.InterLink.entity.ApplicationStatus;
import com.example.InterLink.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
    @RequestMapping("/applications")
    public class ApplicationController {

        private final ApplicationService applicationService;

        @Autowired
        public ApplicationController(ApplicationService applicationService) {
            this.applicationService = applicationService;
        }

        @PostMapping
        public ApplicationEntity createApplication(@RequestBody ApplicationEntity applicationEntity) {
            return applicationService.saveApplication(applicationEntity);
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

