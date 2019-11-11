package com.osh.repository;

import com.osh.domain.Vacation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface VacationRepository extends CrudRepository<Vacation, Integer> {
    List<Vacation> findAllByOrderById();

    List<Vacation> findByCompanyIdOrderById(int companyId);

    List<Vacation> findByDepartmentIdOrderById(int departmentId);
}
