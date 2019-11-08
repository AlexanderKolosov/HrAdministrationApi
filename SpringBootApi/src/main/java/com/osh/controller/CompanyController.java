package com.osh.controller;

import com.osh.domain.Company;
import com.osh.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController // Програмный модуль, который по установленному пути слушает запросы от пользователя и возвращает данные
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/companies")
    public List<Company> getListOfCompanies( ) {

        return companyRepository.findAllByOrderById();
    }

    @PostMapping("/companies")
    public List<Company> createCompany(@RequestBody Company company) {
       Company newCompany = new Company(company.getName());

        newCompany.setCreationDate(LocalDateTime.now());

        companyRepository.save(newCompany);

        return companyRepository.findAllByOrderById();
    }

    @GetMapping("/companies/{id}")
    public Optional<Company> getCompanyById(@PathVariable String id) {

        return companyRepository.findById(Integer.parseInt(id));
    }

    @PutMapping("/companies/{id}")
    public Optional<Company> editCompany(
            @PathVariable String id,
            @RequestBody Company company)
    {
        int intId = Integer.parseInt(id);
        Optional<Company> optionalCompany = companyRepository.findById(intId);

        Company editedCompany = optionalCompany.get();

        editedCompany.setName(company.getName());
        companyRepository.save(editedCompany);

        return Optional.of(editedCompany);
    }

    @DeleteMapping("/companies/{id}")
    public String completelyDeleteCompany(@PathVariable String id) {
        int intId = Integer.parseInt(id);

        Optional<Company> optionalCompany = companyRepository.findById(intId);

        Company deletedCompany = optionalCompany.get();

        companyRepository.deleteById(intId);

        return "Company " + deletedCompany.getName() + " deleted successfully.";
    }
}
