package com.osh.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.osh.domain.Employee;
import com.osh.domain.Views;
import com.osh.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController // Програмный модуль, который по установленному пути слушает запросы от пользователя и возвращает данные
public class EmployeeController {
    private final EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> getListOfEmployees( ) {

        return new ResponseEntity<>(
                employeeService.getAllByOrderById(),
                HttpStatus.OK
        );
    }

    @GetMapping("/employees/companyId{company_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> getListOfEmployeesByCompanyId(
            @PathVariable("company_id") String companyId
    ) {
        int id = Integer.parseInt(companyId);

        return new ResponseEntity<>(
                employeeService.getAllByCompanyIdOrderById(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/employees/departmentId{department_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> getListOfEmployeesByDepartmentId(
            @PathVariable("department_id") String departmentId
    ) {
        int id = Integer.parseInt(departmentId);

        return new ResponseEntity<>(
                employeeService.getAllByDepartmentIdOrderById(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/employees")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> createEmployee(
            @RequestBody Employee employee
            ) {
        Employee newEmployee = new Employee();
        newEmployee.setFirstName(employee.getFirstName());
        newEmployee.setLastName(employee.getLastName());
        newEmployee.setAge(employee.getAge());
        newEmployee.setPassportNumber(employee.getPassportNumber());
        newEmployee.setPhoneNumber(employee.getPhoneNumber());
        newEmployee.setAddress(employee.getAddress());
        newEmployee.setCompanyId(employee.getCompanyId());
        newEmployee.setDepartmentId(employee.getDepartmentId());
        newEmployee.setPosition(employee.getPosition());
        newEmployee.setLogin(employee.getLogin());
        newEmployee.setPassword(employee.getPassword());
        newEmployee.setRole(employee.getRole());
        newEmployee.setCreationDate(LocalDateTime.now());

        employeeService.save(newEmployee);

        return new ResponseEntity<>(
                employeeService.getAllByOrderById(),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/employees/companyId{company_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> createEmployeeByCompanyId(
            @PathVariable("company_id") String companyId,
            @RequestBody Employee employee
    ) {
        int id = Integer.parseInt(companyId);
        Employee newEmployee = new Employee();
        newEmployee.setFirstName(employee.getFirstName());
        newEmployee.setLastName(employee.getLastName());
        newEmployee.setAge(employee.getAge());
        newEmployee.setPassportNumber(employee.getPassportNumber());
        newEmployee.setPhoneNumber(employee.getPhoneNumber());
        newEmployee.setAddress(employee.getAddress());
        newEmployee.setCompanyId(id);
        newEmployee.setDepartmentId(employee.getDepartmentId());
        newEmployee.setPosition(employee.getPosition());
        newEmployee.setLogin(employee.getLogin());
        newEmployee.setPassword(employee.getPassword());
        newEmployee.setRole(employee.getRole());
        newEmployee.setCreationDate(LocalDateTime.now());

        employeeService.save(newEmployee);

        return new ResponseEntity<>(
                employeeService.getAllByCompanyIdOrderById(id),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/employees/departmentId{department_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> createEmployeeByDepartmentId(
            @PathVariable("department_id") String departmentId,
            @RequestBody Employee employee
    ) {
        int id = Integer.parseInt(departmentId);
        Employee newEmployee = new Employee();
        newEmployee.setFirstName(employee.getFirstName());
        newEmployee.setLastName(employee.getLastName());
        newEmployee.setAge(employee.getAge());
        newEmployee.setPassportNumber(employee.getPassportNumber());
        newEmployee.setPhoneNumber(employee.getPhoneNumber());
        newEmployee.setAddress(employee.getAddress());
        newEmployee.setCompanyId(employee.getCompanyId());
        newEmployee.setDepartmentId(id);
        newEmployee.setPosition(employee.getPosition());
        newEmployee.setLogin(employee.getLogin());
        newEmployee.setPassword(employee.getPassword());
        newEmployee.setRole(employee.getRole());
        newEmployee.setCreationDate(LocalDateTime.now());

        employeeService.save(newEmployee);

        return new ResponseEntity<>(
                employeeService.getAllByDepartmentIdOrderById(id),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/employees/{employee_id}")
    public ResponseEntity<?> getEmployeeById(
            @PathVariable("employee_id") String employeeId
    ) {

        return new ResponseEntity<>(
                employeeService.getById(Integer.parseInt(employeeId)),
                HttpStatus.OK
        );
    }

    @PutMapping("/employees/{employee_id}")
    public ResponseEntity<?> editEmployee(
            @PathVariable("employee_id") String employeeId,
            @RequestBody Employee employee)
    {
        int id = Integer.parseInt(employeeId);
        Optional<Employee> optionalEmployee = employeeService.getById(id);

        Employee editedEmployee = optionalEmployee.get();
        editedEmployee.setFirstName(employee.getFirstName());
        editedEmployee.setLastName(employee.getLastName());
        editedEmployee.setAge(employee.getAge());
        editedEmployee.setPassportNumber(employee.getPassportNumber());
        editedEmployee.setPhoneNumber(employee.getPhoneNumber());
        editedEmployee.setAddress(employee.getAddress());
        editedEmployee.setCompanyId(employee.getCompanyId());
        editedEmployee.setDepartmentId(employee.getDepartmentId());
        editedEmployee.setPosition(employee.getPosition());
        editedEmployee.setLogin(employee.getLogin());
        editedEmployee.setPassword(employee.getPassword());
        editedEmployee.setRole(employee.getRole());
        editedEmployee.setCreationDate(LocalDateTime.now());

        employeeService.save(editedEmployee);

        return new ResponseEntity<>(
                employeeService.getById(id),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/employees/{employee_id}")
    public ResponseEntity<?> completelyDeleteCompany(
            @PathVariable("employee_id") String employeeId
    ) {
        int id = Integer.parseInt(employeeId);
        Optional<Employee> optionalEmployee = employeeService.getById(id);

        Employee deletedEmployee = optionalEmployee.get();

        employeeService.deleteById(id);

        return new ResponseEntity<>(
                employeeService.getConfirmationOfDeletionMessage(
                        deletedEmployee.getFirstName() + " " + deletedEmployee.getLastName()),
                HttpStatus.OK
        );
    }
}
