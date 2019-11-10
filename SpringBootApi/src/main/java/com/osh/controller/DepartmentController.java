package com.osh.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.osh.domain.Company;
import com.osh.domain.Department;
import com.osh.domain.Views;
import com.osh.repository.DepartmentRepository;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController // Програмный модуль, который по установленному пути слушает запросы от пользователя и возвращает данные
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/departments")
    @JsonView(Views.UserRoleView.class)
    public List<Department> getListOfDepartments( ) {

        return departmentRepository.findAllByOrderById();
    }

    @GetMapping("/departments/companyId{company_Id}")
    public List<Department> getListOfDepartmentsByCompany(
            @PathVariable("company_Id") String company_Id
    ) {
        int id = Integer.parseInt(company_Id);

        return departmentRepository.findByCompanyId(id);
    }

    @PostMapping("/departments")
    @JsonView(Views.UserRoleView.class)
    public List<Department> createDepartment(
            @RequestBody Department department
    ) {
        Optional<Department> optionalDepartment = departmentRepository.findById(department.getId());

        Department newDepartment = new Department(department.getName(), department.getCompanyId());

        newDepartment.setCreationDate(LocalDateTime.now());

        departmentRepository.save(newDepartment);

        return departmentRepository.findAllByOrderById();
    }

    @PostMapping("/departments/companyId{company_Id}")
    public List<Department> createDepartmentByCompany(
            @PathVariable("company_Id") String company_Id,
            @RequestBody Department department
    ) {
        int id = Integer.parseInt(company_Id);

        Department newDepartment = new Department(department.getName(), id);

        newDepartment.setCreationDate(LocalDateTime.now());

        departmentRepository.save(newDepartment);

        return departmentRepository.findByCompanyId(id);
    }

    @GetMapping("/departments/{department_id}")
    public Optional<Department> getDepartmentById(
            @PathVariable("department_id") String department_id
    ) {
        return departmentRepository.findById(Integer.parseInt(department_id));
    }

    @PutMapping("/departments/{department_id}")
    public Optional<Department> editDepartment(
            @PathVariable("department_id") String department_id,
            @RequestBody Department department)
    {
        int id = Integer.parseInt(department_id);
        Optional<Department> optionalDepartment = departmentRepository.findById(id);

        Department editedDepartment = optionalDepartment.get();

        editedDepartment.setName(department.getName());
        departmentRepository.save(editedDepartment);

        return Optional.of(editedDepartment);
    }

    @DeleteMapping("/departments/{department_id}")
    public String completelyDeleteCompany(
            @PathVariable("department_id") String department_id
    ) {
        int id = Integer.parseInt(department_id);

        Optional<Department> optionalDepartment = departmentRepository.findById(id);

        Department deletedDepartment = optionalDepartment.get();

        departmentRepository.deleteById(id);

        return deletedDepartment.getName() + " deleted successfully.";
    }
}
