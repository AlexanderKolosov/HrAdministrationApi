package com.osh.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.osh.domain.Department;
import com.osh.domain.Views;
import com.osh.service.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController // Програмный модуль, который по установленному пути слушает запросы от пользователя и возвращает данные
public class DepartmentController {
    private final DepartmentServiceImpl departmentService;

    @Autowired
    public DepartmentController(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> getListOfDepartments( ) {

        return new ResponseEntity<>(
                departmentService.getAllByOrderById(),
                HttpStatus.OK
        );
    }

    @GetMapping("/departments/companyId{company_Id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> getListOfDepartmentsByCompany(
            @PathVariable("company_Id") String company_Id
    ) {
        int id = Integer.parseInt(company_Id);

        return new ResponseEntity<>(
                departmentService.getAllByCompanyIdOrderById(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/departments")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> createDepartment(
            @RequestBody Department department
    ) {
        Department newDepartment = new Department(department.getName(), department.getCompanyId());
        newDepartment.setCreationDate(LocalDateTime.now());

        departmentService.save(newDepartment);

        return new ResponseEntity<>(
                departmentService.getAllByOrderById(),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/departments/companyId{company_Id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> createDepartmentByCompany(
            @PathVariable("company_Id") String company_Id,
            @RequestBody Department department
    ) {
        int id = Integer.parseInt(company_Id);
        Department newDepartment = new Department(department.getName(), id);
        newDepartment.setCreationDate(LocalDateTime.now());

        departmentService.save(newDepartment);

        return new ResponseEntity<>(
                departmentService.getAllByCompanyIdOrderById(id),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/departments/{department_id}")
    public ResponseEntity<?> getDepartmentById(
            @PathVariable("department_id") String department_id
    ) {

        return new ResponseEntity<>(
                departmentService.getById(Integer.parseInt(department_id)),
                HttpStatus.OK
        );
    }

    @PutMapping("/departments/{department_id}")
    public ResponseEntity<?> editDepartment(
            @PathVariable("department_id") String department_id,
            @RequestBody Department department)
    {
        int id = Integer.parseInt(department_id);
        Optional<Department> optionalDepartment = departmentService.getById(id);

        Department editedDepartment = optionalDepartment.get();
        editedDepartment.setName(department.getName());

        departmentService.save(editedDepartment);

        return new ResponseEntity<>(
                departmentService.getById(id),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/departments/{department_id}")
    public ResponseEntity<?> completelyDeleteCompany(
            @PathVariable("department_id") String department_id
    ) {
        int id = Integer.parseInt(department_id);
        Optional<Department> optionalDepartment = departmentService.getById(id);

        Department deletedDepartment = optionalDepartment.get();

        departmentService.delete(id);

        return new ResponseEntity<>(
                departmentService.getConfirmationOfDeletionMessage(deletedDepartment.getName()),
                HttpStatus.OK
        );
    }
}
