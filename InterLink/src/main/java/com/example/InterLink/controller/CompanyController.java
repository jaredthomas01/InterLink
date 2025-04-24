package com.example.InterLink.controller;

import com.example.InterLink.entity.CompanyEntity;
import com.example.InterLink.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public CompanyEntity createCompany(@RequestBody CompanyEntity companyEntity) {
        return companyService.saveCompany(companyEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyEntity> getCompanyById(@PathVariable Long id) {
        Optional<CompanyEntity> company = companyService.getCompanyById(id);
        return company.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // ✅ NEW: Get company by associated userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<CompanyEntity> getCompanyByUserId(@PathVariable Long userId) {
        Optional<CompanyEntity> company = companyService.getCompanyByUserId(userId);
        return company.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<CompanyEntity>> getAllCompanies() {
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyEntity> updateCompany(@PathVariable Long id, @RequestBody CompanyEntity companyEntity) {
        CompanyEntity updatedCompany = companyService.updateCompany(id, companyEntity);
        return updatedCompany != null ? new ResponseEntity<>(updatedCompany, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}