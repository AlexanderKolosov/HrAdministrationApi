package com.osh.repository;

import com.osh.domain.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    List<Department> findAllByOrderById();

    List<Department> findByCompanyIdOrderById(int companyId);
}

