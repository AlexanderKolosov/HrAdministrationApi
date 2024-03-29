package com.osh.service;

import com.osh.domain.Vacation;

import java.util.List;
import java.util.Optional;

public interface VacationService {
    List<Vacation> getAllByOrderById();

    List<Vacation> getAllByCompanyIdOrderById(int companyId);

    List<Vacation> getAllByDepartmentIdOrderById(int departmentId);

    List<Vacation> getAllByEmployeeIdOrderById(int employeeId);

    String getConfirmationOfDeletionMessage(int vacationId);

    Optional<Vacation> getById(int vacationId);

    void save(Vacation vacation);

    void deleteById(int vacationId);
}
