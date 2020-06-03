package com.github.loyaltycardwallet.services;

import com.github.loyaltycardwallet.models.Company;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyService {
    List<Company> findAll();

    Optional<Company> findById(UUID id);

    Company save(Company company);

    void deleteById(UUID id);

    boolean existById(UUID id);

    Optional<Company> findByName(String username);
}
