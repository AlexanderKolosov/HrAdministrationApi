package com.osh.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.osh.domain.*;
import com.osh.service.VacationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("vacations")
public class VacationController {
    @Autowired
    private VacationServiceImpl vacationService;

    @Autowired
    private EmployeeController employeeController;

    @GetMapping
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> getListOfVacations( ) {

        return new ResponseEntity<>(
                vacationService.getAllByOrderById(),
                HttpStatus.OK
        );
    }

    @PostMapping
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> createVacation(
            @Valid @RequestBody Vacation vacation
    ) {
        int id = vacation.getCompanyId();
        try {
            Company company = getCompanyController().getCompanyById(id);
        } catch (NoSuchElementException e) {
            return getCompanyController().companyNotFound(id);
        }

        int departmentId = vacation.getDepartmentId();
        try {
            Department department = getDepartmentController().getDepartmentById(departmentId);
        } catch (NoSuchElementException e) {
            return getDepartmentController().departmentNotFound(departmentId);
        }

        int employeeId = vacation.getEmployeeId();
        try {
            Employee employee = employeeController.getEmployeeById(employeeId);
        } catch (NoSuchElementException e) {
            return employeeController.employeeNotFound(employeeId);
        }

        Vacation newVacation = createNewVacationInDatabase(vacation);
        vacationService.save(newVacation);

        return new ResponseEntity<>(
                vacationService.getAllByOrderById(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/companies/{company_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> getListOfVacationsByCompanyId(
            @PathVariable("company_id") String companyId
    ) {
        int id = Integer.parseInt(companyId);
        try {
            Company company = getCompanyController().getCompanyById(id);
        } catch (NoSuchElementException e) {
            return getCompanyController().companyNotFound(id);
        }

        return new ResponseEntity<>(
                vacationService.getAllByCompanyIdOrderById(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/companies/{company_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> createVacationByCompanyId(
            @PathVariable("company_id") String companyId,
            @Valid @RequestBody Vacation vacation
    ) {
        int id = Integer.parseInt(companyId);
        try {
            Company company = getCompanyController().getCompanyById(id);
        } catch (NoSuchElementException e) {
            return getCompanyController().companyNotFound(id);
        }

        int departmentId = vacation.getDepartmentId();
        try {
            Department department = getDepartmentController().getDepartmentById(departmentId);
        } catch (NoSuchElementException e) {
            return getDepartmentController().departmentNotFound(departmentId);
        }

        int employeeId = vacation.getEmployeeId();
        try {
            Employee employee = employeeController.getEmployeeById(employeeId);
        } catch (NoSuchElementException e) {
            return employeeController.employeeNotFound(employeeId);
        }

        vacation.setCompanyId(id);
        Vacation newVacation = createNewVacationInDatabase(vacation);
        vacationService.save(newVacation);

        return new ResponseEntity<>(
                vacationService.getAllByCompanyIdOrderById(id),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/departments/{department_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> getListOfVacationsByDepartmentId(
            @PathVariable("department_id") String departmentId
    ) {
        int id = Integer.parseInt(departmentId);
        try {
            Department department = getDepartmentController().getDepartmentById(id);
        } catch (NoSuchElementException e) {
            return getDepartmentController().departmentNotFound(id);
        }

        return new ResponseEntity<>(
                vacationService.getAllByDepartmentIdOrderById(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/departments/{department_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> createVacationByDepartmentId(
            @PathVariable("department_id") String departmentId,
            @Valid @RequestBody Vacation vacation
    ) {
        int companyId = vacation.getCompanyId();
        try {
            Company company = getCompanyController().getCompanyById(companyId);
        } catch (NoSuchElementException e) {
            return getCompanyController().companyNotFound(companyId);
        }

        int id = Integer.parseInt(departmentId);
        try {
            Department department = getDepartmentController().getDepartmentById(id);
        } catch (NoSuchElementException e) {
            return getDepartmentController().departmentNotFound(id);
        }

        int employeeId = vacation.getEmployeeId();
        try {
            Employee employee = employeeController.getEmployeeById(employeeId);
        } catch (NoSuchElementException e) {
            return employeeController.employeeNotFound(employeeId);
        }

        vacation.setDepartmentId(id);
        Vacation newVacation = createNewVacationInDatabase(vacation);
        vacationService.save(newVacation);

        return new ResponseEntity<>(
                vacationService.getAllByDepartmentIdOrderById(id),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/employees/{employee_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> getListOfVacationsByEmployeeId(
            @PathVariable("employee_id") String employeeId
    ) {
        int id = Integer.parseInt(employeeId);
        try {
            Employee employee = employeeController.getEmployeeById(id);
        } catch (NoSuchElementException e) {
            return employeeController.employeeNotFound(id);
        }

        return new ResponseEntity<>(
                vacationService.getAllByEmployeeIdOrderById(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/employees/{employee_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> createVacationByEmployeeId(
            @PathVariable("employee_id") String employeeId,
            @Valid @RequestBody Vacation vacation
    ) {
        int companyId = vacation.getCompanyId();
        try {
            Company company = getCompanyController().getCompanyById(companyId);
        } catch (NoSuchElementException e) {
            return getCompanyController().companyNotFound(companyId);
        }

        int departmentId = vacation.getDepartmentId();
        try {
            Department department = getDepartmentController().getDepartmentById(departmentId);
        } catch (NoSuchElementException e) {
            return getDepartmentController().departmentNotFound(departmentId);
        }

        int id = Integer.parseInt(employeeId);
        try {
            Employee employee = employeeController.getEmployeeById(id);
        } catch (NoSuchElementException e) {
            return employeeController.employeeNotFound(id);
        }

        vacation.setEmployeeId(id);
        Vacation newVacation = createNewVacationInDatabase(vacation);
        vacationService.save(newVacation);

        return new ResponseEntity<>(
                vacationService.getAllByEmployeeIdOrderById(id),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{vacation_id}")
    public ResponseEntity<?> getVacation(
            @PathVariable("vacation_id") String vacationId
    ) {
        int id = Integer.parseInt(vacationId);
        try {
            Vacation vacation = getVacationById(id);
        } catch (NoSuchElementException e) {
            return vacationNotFound(id);
        }

        return vacationIsFoundById(id);
    }

    @PutMapping("/{vacation_id}")
    public ResponseEntity<?> editVacation(
            @PathVariable("vacation_id") String vacationId,
            @Valid @RequestBody Vacation vacation
    ) {
        int companyId = vacation.getCompanyId();
        try {
            Company company = getCompanyController().getCompanyById(companyId);
        } catch (NoSuchElementException e) {
            return getCompanyController().companyNotFound(companyId);
        }

        int departmentId = vacation.getDepartmentId();
        try {
            Department department = getDepartmentController().getDepartmentById(departmentId);
        } catch (NoSuchElementException e) {
            return getDepartmentController().departmentNotFound(departmentId);
        }

        int employeeId = vacation.getEmployeeId();
        try {
            Employee employee = employeeController.getEmployeeById(employeeId);
        } catch (NoSuchElementException e) {
            return employeeController.employeeNotFound(employeeId);
        }

        int id = Integer.parseInt(vacationId);
        Vacation editedVacation;
        try {
             editedVacation = getVacationById(id);
             fillVacationData(editedVacation, vacation);
        } catch (NoSuchElementException e) {
            return vacationNotFound(id);
        }

        vacationService.save(editedVacation);

        return vacationIsFoundById(id);
    }

    @DeleteMapping("/{vacation_id}")
    public ResponseEntity<?> completelyDeleteCompany(
            @PathVariable("vacation_id") String vacationId
    ) {
        int id = Integer.parseInt(vacationId);
        Vacation deletedVacation;
        try {
            deletedVacation = getVacationById(id);
        } catch (NoSuchElementException e) {
            return vacationNotFound(id);
        }

        vacationService.deleteById(id);

        return new ResponseEntity<>(
                vacationService.getConfirmationOfDeletionMessage(id),
                HttpStatus.OK
        );
    }

    private Vacation getVacationById(int id) throws NoSuchElementException {
        Optional<Vacation> optionalVacation = vacationService.getById(id);

        return optionalVacation.get();
    }

    private Vacation createNewVacationInDatabase(Vacation vacation) {
        Vacation newVacation = new Vacation();

        fillVacationData(newVacation, vacation);

        return newVacation;
    }

    private void fillVacationData(Vacation available, Vacation requestBody) {
        available.setEmployeeId(requestBody.getEmployeeId());
        available.setCompanyId(requestBody.getCompanyId());
        available.setDepartmentId(requestBody.getDepartmentId());
        available.setStartDate(requestBody.getStartDate());
        available.setPeriodOfDays(requestBody.getPeriodOfDays());
        available.setStatus("In Process");
        available.setCreationDate(LocalDateTime.now());
    }

    private ResponseEntity<?> vacationNotFound(int id) {
        return new ResponseEntity<>(
                "Vacation with id #" + id + " not found in the database.",
                HttpStatus.BAD_REQUEST
        );
    }

    private ResponseEntity<?> vacationIsFoundById(int id) {
        return new ResponseEntity<>(
                vacationService.getById(id),
                HttpStatus.OK
        );
    }

    private CompanyController getCompanyController() {
        return employeeController.getCompanyController();
    }

    private DepartmentController getDepartmentController() {
        return employeeController.getDepartmentController();
    }
}
