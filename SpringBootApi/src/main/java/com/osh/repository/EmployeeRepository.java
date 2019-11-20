package com.osh.repository;

import com.osh.domain.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    List<Employee> findAllByOrderById();

    List<Employee> findByCompanyIdOrderById(int companyId);

    List<Employee> findByDepartmentIdOrderById(int departmentId);

    Optional<Employee> findByLogin(String login);
}

