package com.osh.service;

import com.osh.domain.Company;
import com.osh.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllByOrderById() {
        return companyRepository.findAllByOrderById();
    }

    @Override
    public Optional<Company> getById(int companyId) {
        return companyRepository.findById(companyId);
    }

    @Override
    public String getConfirmationOfDeletionMessage(String companyName) {
        return "Company " + companyName + " deleted successfully.";
    }

    @Override
    public void save(Company company) {
        companyRepository.save(company);
    }

    @Override
    public void deleteById(int id) {
        companyRepository.deleteById(id);
    }
}
