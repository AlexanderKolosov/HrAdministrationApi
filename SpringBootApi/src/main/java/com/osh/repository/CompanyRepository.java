package com.osh.repository;

import com.osh.domain.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends CrudRepository<Company, Integer> {
    List<Company> findAllByOrderById();

    Optional<Company> findByName(String companyName);
}

