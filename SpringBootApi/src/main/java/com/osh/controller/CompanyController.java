package com.osh.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.osh.domain.Company;
import com.osh.domain.Views;
import com.osh.service.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("companies")
public class CompanyController {
    @Autowired
    private CompanyServiceImpl companyService;

    @GetMapping
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> getListOfCompanies( ) {

        return new ResponseEntity<>(
                companyService.getAllByOrderById(),
                HttpStatus.OK
        );
    }

    @PostMapping
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> createCompany(
            @Valid @RequestBody Company company
    ) {
        Company newCompany = new Company(company.getName());

        newCompany.setCreationDate(LocalDateTime.now());
        companyService.save(newCompany);

        return new ResponseEntity<>(
                companyService.getAllByOrderById(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{company_id}")
    public ResponseEntity<?> getCompanyById(
            @PathVariable("company_id") String companyId
    ) {
        int id = Integer.parseInt(companyId);
        try {
            Company company = getCompanyById(id);
        }catch (NoSuchElementException e) {
            return companyNotFound(id);
        }

        return companyIsFoundById(id);
    }

    @PutMapping("/{company_id}")
    public ResponseEntity<?> editCompany(
            @PathVariable("company_id") String companyId,
            @Valid @RequestBody Company company)
    {
        int id = Integer.parseInt(companyId);
        try {
            Company editedCompany = getCompanyById(id);
            editedCompany.setName(company.getName());

            companyService.save(editedCompany);
        }catch (NoSuchElementException e) {
            return companyNotFound(id);
        }

        return companyIsFoundById(id);
    }

    @DeleteMapping("/{company_id}")
    public ResponseEntity<?> deleteCompany(
            @PathVariable("company_id") String companyId
    ) {
        int id = Integer.parseInt(companyId);
        Company deletedCompany = null;
        try {
            deletedCompany = getCompanyById(id);
        }catch (NoSuchElementException e) {
            return companyNotFound(id);
        }

        companyService.deleteById(id);

        return new ResponseEntity<>(
                companyService.getConfirmationOfDeletionMessage(deletedCompany.getName()),
                HttpStatus.OK
        );
    }

    Company getCompanyById(int id) throws NoSuchElementException {
        Optional<Company> optionalCompany = companyService.getById(id);

        return optionalCompany.get();
    }

    Company getCompanyByName(String companyName) throws NoSuchElementException {
        Optional<Company> optionalCompany = companyService.getByName(companyName);

        return optionalCompany.get();
    }

    ResponseEntity<?> companyNotFound(int id) {
        return new ResponseEntity<>(
                "Company with id #" + id + " not found in the database.",
                HttpStatus.BAD_REQUEST
        );
    }

    private ResponseEntity<?> companyIsFoundById(int id) {
        return new ResponseEntity<>(
                companyService.getById(id),
                HttpStatus.OK
        );
    }

    public CompanyServiceImpl getCompanyService() {
        return companyService;
    }
}
