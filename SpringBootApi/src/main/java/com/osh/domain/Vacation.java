package com.osh.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.UserRoleView.class)
    private int id;

    @Column(updatable = false)
    @JsonView(Views.UserRoleView.class)
    private int employeeId;

    @Column(updatable = false)
    @JsonView(Views.UserRoleView.class)
    private int companyId;

    @Column(updatable = false)
    @JsonView(Views.UserRoleView.class)
    private int departmentId;

    @Column(updatable = false)
    @JsonView(Views.UserRoleView.class)
    private String startVacationDate;

    @Column(updatable = false)
    @JsonView(Views.UserRoleView.class)
    private int periodOfDays;

    @JsonView(Views.UserRoleView.class)
    private String status;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime creationDate;

    public Vacation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getStartVacationDate() {
        return startVacationDate;
    }

    public void setStartVacationDate(String startVacationDate) {
        this.startVacationDate = startVacationDate;
    }

    public int getPeriodOfDays() {
        return periodOfDays;
    }

    public void setPeriodOfDays(int periodOfDays) {
        this.periodOfDays = periodOfDays;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
