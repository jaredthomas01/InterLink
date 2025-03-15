package com.example.InterLink.service.impl;

import com.example.InterLink.entity.CompanyEntity;
import com.example.InterLink.repository.CompanyRepository;
import com.example.InterLink.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
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
