package com.osh.service;

import com.osh.domain.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllByOrderById();

    List<Employee> getAllByCompanyIdOrderById(int companyId);

    List<Employee> getAllByDepartmentIdOrderById(int departmentId);

    Optional<Employee> getById(int employeeId);

    String getConfirmationOfDeletionMessage(String employeeFullname);

    void save(Employee employee);

    void deleteById(int employeeId);
}
