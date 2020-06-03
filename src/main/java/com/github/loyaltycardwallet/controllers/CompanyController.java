package com.github.loyaltycardwallet.controllers;

import com.github.loyaltycardwallet.models.Company;
import com.github.loyaltycardwallet.services.implementation.CompanyServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin
public class CompanyController {
    private CompanyServiceImpl companyService;

    public CompanyController(CompanyServiceImpl companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAll() {
        return ResponseEntity.ok(companyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable UUID id) {
        return companyService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Company> add(@RequestBody @Valid Company company) {
        Company newCompany = companyService.save(company);
        return ResponseEntity.created(URI.create("/" + newCompany.getId())).body(newCompany);
    }

    @PutMapping(value = {"/id"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Company> update(@PathVariable UUID id, @RequestBody Company company) {
        if (!companyService.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        company.setId(id);
        companyService.save(company);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable UUID id) {
        companyService.deleteById(id);
        if (!companyService.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

