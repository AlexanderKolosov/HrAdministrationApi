package com.osh.service;

import com.osh.domain.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    List<Company> getAllByOrderById();

    Optional<Company> getById(int companyId);

    Optional<Company> getByName(String companyName);

    String getConfirmationOfDeletionMessage(String companyName);

    void save(Company company);

    void deleteById(int id);
}
