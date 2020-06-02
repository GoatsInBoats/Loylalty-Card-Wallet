package com.github.loyaltycardwallet.repositories;

import com.github.loyaltycardwallet.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
    //should be company here instead of user
    Optional<Company> findByCompanyName(String companyName);

    boolean existsById(UUID id);
}
