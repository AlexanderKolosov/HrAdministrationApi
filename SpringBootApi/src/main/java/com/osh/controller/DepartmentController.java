package com.osh.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.osh.domain.*;
import com.osh.service.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("departments")
public class DepartmentController {
    @Autowired
    private DepartmentServiceImpl departmentService;

    @Autowired
    private CompanyController companyController;

    @GetMapping
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> getListOfDepartments( ) {

        return new ResponseEntity<>(
                departmentService.getAllByOrderById(),
                HttpStatus.OK
        );
    }

    @PostMapping
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> createDepartment(
            @Valid  @RequestBody Department department
    ) {
        int id = department.getCompanyId();
        try {
            Company company = companyController.getCompanyById(id);
        } catch (NoSuchElementException e) {
            return companyController.companyNotFound(id);
        }

        Department newDepartment = createNewDepartmentInDatabase(department);

        return new ResponseEntity<>(
                departmentService.getAllByOrderById(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/companies/{company_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> getListOfDepartmentsByCompanyId(
            @PathVariable("company_id") String companyId
    ) {
        int id = Integer.parseInt(companyId);
        try {
            Company company = companyController.getCompanyById(id);
        }catch (NoSuchElementException e) {
            return companyController.companyNotFound(id);
        }

        return new ResponseEntity<>(
                departmentService.getAllByCompanyIdOrderById(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/companies/{company_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> createDepartmentByCompanyId(
            @PathVariable("company_id") String companyId,
            @Valid @RequestBody Department department
    ) {
        int id = Integer.parseInt(companyId);
        try {
            Company company = companyController.getCompanyById(id);
        }catch (NoSuchElementException e) {
            return departmentNotFound(id);
        }

        department.setCompanyId(id);
        Department newDepartment = createNewDepartmentInDatabase(department);

        return new ResponseEntity<>(
                departmentService.getAllByCompanyIdOrderById(id),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{department_id}")
    public ResponseEntity<?> getDepartmentById(
            @PathVariable("department_id") String departmentId
    ) {
        int id = Integer.parseInt(departmentId);
        try {
            Department department = getDepartmentById(id);
        }catch (NoSuchElementException e) {
            return departmentNotFound(id);
        }

        return departmentIsFoundById(id);
    }

    @PutMapping("/{department_id}")
    public ResponseEntity<?> editDepartment(
            @PathVariable("department_id") String departmentId,
            @Valid @RequestBody Department department)
    {
        int companyId = department.getCompanyId();
        try {
            Company company = companyController.getCompanyById(companyId);
        }catch (NoSuchElementException e) {
            return companyController.companyNotFound(companyId);
        }

        int id = Integer.parseInt(departmentId);
        try {
            Department editedDepartment = getDepartmentById(id);
            editedDepartment.setName(department.getName());

            departmentService.save(editedDepartment);
        }catch (NoSuchElementException e) {
            return departmentNotFound(id);
        }

        return departmentIsFoundById(id);
    }

    @DeleteMapping("/{department_id}")
    public ResponseEntity<?> deleteDepartment(
            @PathVariable("department_id") String departmentId
    ) {
        int id = Integer.parseInt(departmentId);
        Department deletedDepartment;
        try {
            deletedDepartment = getDepartmentById(id);
        } catch (NoSuchElementException e) {
            return departmentNotFound(id);
        }

        departmentService.deleteById(id);

        return new ResponseEntity<>(
                departmentService.getConfirmationOfDeletionMessage(deletedDepartment.getName()),
                HttpStatus.OK
        );
    }

    Department getDepartmentById(int id) throws NoSuchElementException {
        Optional<Department> optionalDepartment = departmentService.getById(id);

        return optionalDepartment.get();
    }

    private Department createNewDepartmentInDatabase(Department department) {
        Department newDepartment = new Department(department.getName());
        newDepartment.setCompanyId(department.getCompanyId());
        newDepartment.setCreationDate(LocalDateTime.now());

        departmentService.save(newDepartment);

        return newDepartment;
    }

    ResponseEntity<?> departmentNotFound(int id) {
        return new ResponseEntity<>(
                "Department with id #" + id + " not found in the database.",
                HttpStatus.BAD_REQUEST
        );
    }

    private ResponseEntity<?> departmentIsFoundById(int id) {
        return new ResponseEntity<>(
                departmentService.getById(id),
                HttpStatus.OK
        );
    }

    CompanyController getCompanyController() {
        return companyController;
    }
}
