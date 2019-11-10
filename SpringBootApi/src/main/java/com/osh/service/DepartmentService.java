package com.osh.service;

import com.osh.domain.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    List<Department> getAllByOrderById();

    List<Department> getAllByCompanyIdOrderById(int companyId);

    Optional<Department> getById(int departmentId);

    String getConfirmationOfDeletionMessage(String departmentName);

    void save(Department department);

    void delete(int id);
}
