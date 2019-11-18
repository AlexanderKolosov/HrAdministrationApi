package com.osh.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.osh.domain.*;
import com.osh.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private DepartmentController departmentController;

    @GetMapping
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> getListOfEmployees( ) {

        return new ResponseEntity<>(
                employeeService.getAllByOrderById(),
                HttpStatus.OK
        );
    }

    @PostMapping
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> createEmployee(
            @Valid @RequestBody Employee employee
    ) {
        int companyId = employee.getCompanyId();
        try {
            Company company = getCompanyController().getCompanyById(companyId);
        } catch (NoSuchElementException e) {
            return getCompanyController().companyNotFound(companyId);
        }

        int departmentId = employee.getDepartmentId();
        try {
            Department department = departmentController.getDepartmentById(departmentId);
        } catch (NoSuchElementException e) {
            return departmentController.departmentNotFound(departmentId);
        }

        Employee newEmployee = createNewEmployeeInDatabase(employee);
        employeeService.save(newEmployee);

        return new ResponseEntity<>(
                employeeService.getAllByOrderById(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/companies/{company_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> getListOfEmployeesByCompanyId(
            @PathVariable("company_id") String companyId
    ) {
        int id = Integer.parseInt(companyId);
        try {
            Company company = getCompanyController().getCompanyById(id);
        } catch (NoSuchElementException e) {
            return getCompanyController().companyNotFound(id);
        }

        return new ResponseEntity<>(
                employeeService.getAllByCompanyIdOrderById(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/companies/{company_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> createEmployeeByCompanyId(
            @PathVariable("company_id") String companyId,
            @Valid @RequestBody Employee employee
    ) {
        int id = Integer.parseInt(companyId);
        try {
            Company company = getCompanyController().getCompanyById(id);
        } catch (NoSuchElementException e) {
            return getCompanyController().companyNotFound(id);
        }

        int departmentId = employee.getDepartmentId();
        try {
            Department department = departmentController.getDepartmentById(departmentId);
        } catch (NoSuchElementException e) {
            return departmentController.departmentNotFound(departmentId);
        }

        employee.setCompanyId(id);
        Employee newEmployee = createNewEmployeeInDatabase(employee);
        employeeService.save(newEmployee);

        return new ResponseEntity<>(
                employeeService.getAllByCompanyIdOrderById(id),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/departments/{department_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> getListOfEmployeesByDepartmentId(
            @PathVariable("department_id") String departmentId
    ) {
        int id = Integer.parseInt(departmentId);
        try {
            Department department = departmentController.getDepartmentById(id);
        } catch (NoSuchElementException e) {
            return departmentController.departmentNotFound(id);
        }

        return new ResponseEntity<>(
                employeeService.getAllByDepartmentIdOrderById(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/departments/{department_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> createEmployeeByDepartmentId(
            @PathVariable("department_id") String departmentId,
            @Valid @RequestBody Employee employee
    ) {
        int id = Integer.parseInt(departmentId);
        try {
            Department department = departmentController.getDepartmentById(id);
        } catch (NoSuchElementException e) {
            return departmentController.departmentNotFound(id);
        }

        int companyId = employee.getCompanyId();
        try {
            Company company = getCompanyController().getCompanyById(companyId);
        } catch (NoSuchElementException e) {
            return getCompanyController().companyNotFound(companyId);
        }

        employee.setDepartmentId(id);
        Employee newEmployee = createNewEmployeeInDatabase(employee);
        employeeService.save(newEmployee);

        return new ResponseEntity<>(
                employeeService.getAllByDepartmentIdOrderById(id),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{employee_id}")
    public ResponseEntity<?> getEmployeeById(
            @PathVariable("employee_id") String employeeId
    ) {
        int id = Integer.parseInt(employeeId);
        try {
            Employee employee = getEmployeeById(id);
        } catch (NoSuchElementException e) {
            return employeeNotFound(id);
        }

        return employeeIsFoundById(id);
    }

    @PutMapping("/{employee_id}")
    public ResponseEntity<?> editEmployee(
            @PathVariable("employee_id") String employeeId,
            @Valid @RequestBody Employee employee)
    {
        int departmentId = employee.getDepartmentId();
        try {
            Department department = departmentController.getDepartmentById(departmentId);
        } catch (NoSuchElementException e) {
            return departmentController.departmentNotFound(departmentId);
        }
        int companyId = employee.getCompanyId();
        try {
            Company company = getCompanyController().getCompanyById(companyId);
        } catch (NoSuchElementException e) {
            return getCompanyController().companyNotFound(companyId);
        }

        int id = Integer.parseInt(employeeId);
        Employee editedEmployee;
        try {
            editedEmployee = getEmployeeById(id);
            fillEmployeeData(editedEmployee, employee);
        } catch (NoSuchElementException e) {
            return employeeNotFound(id);
        }

        employeeService.save(editedEmployee);

        return employeeIsFoundById(id);
    }

    @DeleteMapping("/{employee_id}")
    public ResponseEntity<?> completelyDeleteCompany(
            @PathVariable("employee_id") String employeeId
    ) {
        int id = Integer.parseInt(employeeId);
        Employee deletedEmployee;
        try {
            deletedEmployee = getEmployeeById(id);
        } catch (NoSuchElementException e) {
            return employeeNotFound(id);
        }

        employeeService.deleteById(id);

        return new ResponseEntity<>(
                employeeService.getConfirmationOfDeletionMessage(
                        deletedEmployee.getFirstName() + " " + deletedEmployee.getLastName()),
                HttpStatus.OK
        );
    }

    Employee getEmployeeById(int id) throws NoSuchElementException {
        Optional<Employee> optionalEmployee = employeeService.getById(id);

        return optionalEmployee.get();
    }

    private Employee createNewEmployeeInDatabase(Employee employee) {
        Employee newEmployee = new Employee();

        fillEmployeeData(newEmployee, employee);

        return newEmployee;
    }

    private void fillEmployeeData(Employee available, Employee requestBody) {
        available.setFirstName(requestBody.getFirstName());
        available.setLastName(requestBody.getLastName());
        available.setAge(requestBody.getAge());
        available.setPassportNumber(requestBody.getPassportNumber());
        available.setPhoneNumber(requestBody.getPhoneNumber());
        available.setAddress(requestBody.getAddress());
        available.setCompanyId(requestBody.getCompanyId());
        available.setDepartmentId(requestBody.getDepartmentId());
        available.setPosition(requestBody.getPosition());
        available.setLogin(requestBody.getLogin());
        available.setPassword(requestBody.getPassword());
        available.setRoles(requestBody.getRoles());
        available.setCreationDate(LocalDateTime.now());
    }

    ResponseEntity<?> employeeNotFound(int id) {
        return new ResponseEntity<>(
                "Employee with id #" + id + " not found in the database.",
                HttpStatus.BAD_REQUEST
        );
    }

    private ResponseEntity<?> employeeIsFoundById(int id) {
        return new ResponseEntity<>(
                employeeService.getById(id),
                HttpStatus.OK
        );
    }

    CompanyController getCompanyController() {
        return departmentController.getCompanyController();
    }

    DepartmentController getDepartmentController() {
        return departmentController;
    }
}
