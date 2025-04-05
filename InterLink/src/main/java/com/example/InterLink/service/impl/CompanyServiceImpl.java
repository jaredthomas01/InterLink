package com.example.InterLink.service.impl;

import com.example.InterLink.entity.CompanyEntity;
import com.example.InterLink.entity.UserEntity;
import com.example.InterLink.repository.CompanyRepository;
import com.example.InterLink.repository.UserRepository;
import com.example.InterLink.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    @Autowired
    private final UserRepository userRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CompanyEntity> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<CompanyEntity> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public CompanyEntity saveCompany(CompanyEntity companyEntity) {
        if (companyEntity.getUser() == null || companyEntity.getUser().getId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        // Prevent duplicate company for same user
        Optional<CompanyEntity> existing = companyRepository.findByUser_Id(companyEntity.getUser().getId());
        if (existing.isPresent()) {
            throw new IllegalStateException("A company already exists for this user.");
        }

        return companyRepository.save(companyEntity);
    }

    @Override
    public CompanyEntity updateCompany(Long id, CompanyEntity companyEntity) {
        return companyRepository.findById(id).map(existingCompany -> {
            existingCompany.setName(companyEntity.getName());
            existingCompany.setIndustry(companyEntity.getIndustry());
            existingCompany.setLocation(companyEntity.getLocation());
            return companyRepository.save(existingCompany);
        }).orElse(null);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
