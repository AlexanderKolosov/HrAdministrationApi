package com.osh.repository;

import com.osh.domain.Department;
import com.osh.domain.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    List<Employee> findAllByOrderById();

}

