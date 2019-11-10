package com.osh.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.osh.domain.Company;
import com.osh.domain.Views;
import com.osh.service.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController // Програмный модуль, который по установленному пути слушает запросы от пользователя и возвращает данные
public class CompanyController {
    private final CompanyServiceImpl companyService;

    @Autowired
    public CompanyController(CompanyServiceImpl companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> getListOfCompanies( ) {

        return new ResponseEntity<>(
                companyService.getAllByOrderById(),
                HttpStatus.OK
        );
    }

    @PostMapping("/companies")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> createCompany(
            @RequestBody Company company
    ) {
        Company newCompany = new Company(company.getName());
        newCompany.setCreationDate(LocalDateTime.now());
        companyService.save(newCompany);

        return new ResponseEntity<>(
                companyService.getAllByOrderById(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/companies/{company_id}")
    public ResponseEntity<?> getCompanyById(
            @PathVariable("company_id") String companyId
    ) {
        int id = Integer.parseInt(companyId);

        return new ResponseEntity<>(
                companyService.getById(id),
                HttpStatus.OK
        );
    }

    @PutMapping("/companies/{company_id}")
    public ResponseEntity<?> editCompany(
            @PathVariable("company_id") String companyId,
            @RequestBody Company company)
    {
        int id = Integer.parseInt(companyId);
        Optional<Company> optionalCompany = companyService.getById(id);

        Company editedCompany = optionalCompany.get();
        editedCompany.setName(company.getName());

        companyService.save(editedCompany);

        return new ResponseEntity<>(
                companyService.getById(id),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/companies/{company_id}")
    public ResponseEntity<?> completelyDeleteCompany(
            @PathVariable("company_id") String companyId
    ) {
        int id = Integer.parseInt(companyId);
        Optional<Company> optionalCompany = companyService.getById(id);

        Company deletedCompany = optionalCompany.get();
        companyService.delete(id);

        return new ResponseEntity<>(
                companyService.getConfirmationOfDeletionMessage(deletedCompany.getName()),
                HttpStatus.OK
        );
    }
}
