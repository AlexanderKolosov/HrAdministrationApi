package com.osh.service;

import com.osh.domain.Vacation;
import com.osh.repository.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacationServiceImpl implements VacationService {
    private final VacationRepository vacationRepository;

    @Autowired
    public VacationServiceImpl(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

    @Override
    public List<Vacation> getAllByOrderById() {
        return vacationRepository.findAllByOrderById();
    }

    @Override
    public List<Vacation> getAllByCompanyIdOrderById(int companyId) {
        return vacationRepository.findByCompanyIdOrderById(companyId);
    }

    @Override
    public List<Vacation> getAllByDepartmentIdOrderById(int departmentId) {
        return vacationRepository.findByDepartmentIdOrderById(departmentId);
    }

    @Override
    public Optional<Vacation> getById(int vacationId) {
        return vacationRepository.findById(vacationId);
    }

    @Override
    public void save(Vacation vacation) {
        vacationRepository.save(vacation);
    }
}
