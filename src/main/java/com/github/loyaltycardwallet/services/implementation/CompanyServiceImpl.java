package com.github.loyaltycardwallet.services.implementation;

import com.github.loyaltycardwallet.models.Company;
import com.github.loyaltycardwallet.repositories.CompanyRepository;
import com.github.loyaltycardwallet.services.CompanyService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Service
class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<Company> findCompanyByStampCardId(UUID scId) {
        return companyRepository.findCompanyByStampCardId(scId);
    }

    @Override
    public Optional<Company> findById(UUID id) {
        return companyRepository.findById(id);
    }

    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void deleteById(UUID id) {
        companyRepository.deleteById(id);
    }

    @Override
    public boolean existById(UUID id) {
        return companyRepository.existsById(id);
    }

}
