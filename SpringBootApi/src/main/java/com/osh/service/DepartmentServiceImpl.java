package com.osh.service;

import com.osh.domain.Department;
import com.osh.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService{
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> getAllByOrderById() {
        return departmentRepository.findAllByOrderById();
    }

    @Override
    public List<Department> getAllByCompanyIdOrderById(int companyId) {
        return departmentRepository.findByCompanyIdOrderById(companyId);
    }

    @Override
    public Optional<Department> getById(int departmentId) {
        return departmentRepository.findById(departmentId);
    }

    @Override
    public String getConfirmationOfDeletionMessage(String departmentName) {
        return departmentName + " deleted successfully.";
    }

    @Override
    public void save(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public void delete(int id) {
        departmentRepository.deleteById(id);
    }
}
