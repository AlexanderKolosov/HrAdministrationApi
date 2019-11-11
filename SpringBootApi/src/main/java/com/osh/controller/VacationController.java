package com.osh.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.osh.domain.Vacation;
import com.osh.domain.Views;
import com.osh.service.VacationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("vacations")
public class VacationController {
    private final VacationServiceImpl vacationService;

    public VacationController(VacationServiceImpl vacationService) {
        this.vacationService = vacationService;
    }

    @GetMapping
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> getListOfVacations( ) {

        return new ResponseEntity<>(
                vacationService.getAllByOrderById(),
                HttpStatus.OK
        );
    }

    @GetMapping("/companyId{company_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> getListOfVacationsByCompanyId(
            @PathVariable("company_id") String companyId
    ) {
        int id = Integer.parseInt(companyId);

        return new ResponseEntity<>(
                vacationService.getAllByCompanyIdOrderById(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/departmentId{department_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> getListOfVacationsByDepartmentId(
            @PathVariable("department_id") String departmentId
    ) {
        int id = Integer.parseInt(departmentId);

        return new ResponseEntity<>(
                vacationService.getAllByDepartmentIdOrderById(id),
                HttpStatus.OK
        );
    }

    @PostMapping
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> createVacation(
            @RequestBody Vacation vacation
    ) {
        Vacation newVacation = new Vacation();
        newVacation.setEmployeeId(vacation.getEmployeeId());
        newVacation.setCompanyId(vacation.getCompanyId());
        newVacation.setDepartmentId(vacation.getDepartmentId());
        newVacation.setStartVacationDate(vacation.getStartVacationDate());
        newVacation.setPeriodOfDays(vacation.getPeriodOfDays());
        newVacation.setStatus("In Process");
        newVacation.setCreationDate(LocalDateTime.now());

        vacationService.save(newVacation);

        return new ResponseEntity<>(
                vacationService.getAllByOrderById(),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/companyId{company_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> createVacationByCompanyId(
            @PathVariable("company_id") String companyId,
            @RequestBody Vacation vacation
    ) {
        int id = Integer.parseInt(companyId);

        Vacation newVacation = new Vacation();
        newVacation.setEmployeeId(vacation.getEmployeeId());
        newVacation.setCompanyId(id);
        newVacation.setDepartmentId(vacation.getDepartmentId());
        newVacation.setStartVacationDate(vacation.getStartVacationDate());
        newVacation.setPeriodOfDays(vacation.getPeriodOfDays());
        newVacation.setStatus("In Process");
        newVacation.setCreationDate(LocalDateTime.now());

        vacationService.save(newVacation);

        return new ResponseEntity<>(
                vacationService.getAllByCompanyIdOrderById(id),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/departmentId{department_id}")
    @JsonView(Views.UserRoleView.class)
    public ResponseEntity<?> createVacationByDepartmentId(
            @PathVariable("department_id") String departmentId,
            @RequestBody Vacation vacation
    ) {
        int id = Integer.parseInt(departmentId);

        Vacation newVacation = new Vacation();
        newVacation.setEmployeeId(vacation.getEmployeeId());
        newVacation.setCompanyId(vacation.getCompanyId());
        newVacation.setDepartmentId(id);
        newVacation.setStartVacationDate(vacation.getStartVacationDate());
        newVacation.setPeriodOfDays(vacation.getPeriodOfDays());
        newVacation.setStatus("In Process");
        newVacation.setCreationDate(LocalDateTime.now());

        vacationService.save(newVacation);

        return new ResponseEntity<>(
                vacationService.getAllByDepartmentIdOrderById(id),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{vacation_id}")
    public ResponseEntity<?> getVacation(
            @PathVariable("vacation_id") String vacationId
    ) {

        return new ResponseEntity<>(
                vacationService.getById(Integer.parseInt(vacationId)),
                HttpStatus.OK
        );
    }

    @PutMapping("/{vacation_id}")
    public ResponseEntity<?> editVacation(
            @PathVariable("vacation_id") String vacationId,
            @RequestBody Vacation vacation
    ) {
        int id = Integer.parseInt(vacationId);
        Optional<Vacation> optionalVacation = vacationService.getById(id);

        Vacation editedVacation = optionalVacation.get();
        editedVacation.setStatus(vacation.getStatus());

        vacationService.save(editedVacation);

        return new ResponseEntity<>(
                vacationService.getById(id),
                HttpStatus.OK
        );
    }
}
