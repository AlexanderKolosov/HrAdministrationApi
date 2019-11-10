package com.osh.service;

import com.osh.domain.Employee;
import com.osh.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllByOrderById() {
        return employeeRepository.findAllByOrderById();
    }

    @Override
    public List<Employee> getAllByCompanyIdOrderById(int companyId) {
        return employeeRepository.findByCompanyIdOrderById(companyId);
    }

    @Override
    public List<Employee> getAllByDepartmentIdOrderById(int departmentId) {
        return employeeRepository.findByDepartmentIdOrderById(departmentId);
    }

    @Override
    public Optional<Employee> getById(int departmentId) {
        return employeeRepository.findById(departmentId);
    }

    @Override
    public String getConfirmationOfDeletionMessage(String employeeFullname) {
        return employeeFullname + " deleted successfully.";
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteById(int employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
