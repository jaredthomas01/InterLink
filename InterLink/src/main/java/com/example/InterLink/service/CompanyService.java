package com.example.InterLink.service;

import com.example.InterLink.entity.CompanyEntity;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    List<CompanyEntity> getAllCompanies();
    Optional<CompanyEntity> getCompanyById(Long id);
    Optional<CompanyEntity> getCompanyByUserId(Long userId);
    CompanyEntity saveCompany(CompanyEntity companyEntity);
    CompanyEntity updateCompany(Long id, CompanyEntity companyEntity);
    void deleteCompany(Long id);
}
