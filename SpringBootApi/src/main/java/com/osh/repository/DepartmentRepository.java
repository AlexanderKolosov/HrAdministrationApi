package com.osh.repository;

import com.osh.domain.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    List<Department> findAllByOrderById();

    List<Department> findByCompanyIdOrderById(int companyId);
}

