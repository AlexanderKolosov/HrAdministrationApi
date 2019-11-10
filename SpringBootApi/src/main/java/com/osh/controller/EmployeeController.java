package com.osh.controller;

import com.osh.domain.Department;
import com.osh.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController // Програмный модуль, который по установленному пути слушает запросы от пользователя и возвращает данные
public class EmployeeController {
    @Autowired
    private DepartmentRepository departmentRepository;

    /*@GetMapping("/departments")
    public List<Department> getListOfDepartments( ) {

        return departmentRepository.findAllByOrderById();
    }

    @PostMapping("/departments")
    public List<Department> createCompany(@RequestBody Department department) {
       Department newDepartment = new Department(department.getName(), department.getCompanyId());

        newDepartment.setCreationDate(LocalDateTime.now());

        departmentRepository.save(newDepartment);

        return departmentRepository.findAllByOrderById();
    }

    @GetMapping("/departments/{id}")
    public Optional<Department> getDepartmentById(@PathVariable String id) {

        return departmentRepository.findById(Integer.parseInt(id));
    }

    @PutMapping("/departments/{id}")
    public Optional<Department> editDepartment(
            @PathVariable String id,
            @RequestBody Department department)
    {
        int intId = Integer.parseInt(id);
        Optional<Department> optionalDepartment = departmentRepository.findById(intId);

        Department editedDepartment = optionalDepartment.get();

        editedDepartment.setName(department.getName());
        departmentRepository.save(editedDepartment);

        return Optional.of(editedDepartment);
    }

    @DeleteMapping("/departments/{id}")
    public String completelyDeleteCompany(@PathVariable String id) {
        int intId = Integer.parseInt(id);

        Optional<Department> optionalDepartment = departmentRepository.findById(intId);

        Department deletedDepartment = optionalDepartment.get();

        departmentRepository.deleteById(intId);

        return deletedDepartment.getName() + " deleted successfully.";
    }*/
}
