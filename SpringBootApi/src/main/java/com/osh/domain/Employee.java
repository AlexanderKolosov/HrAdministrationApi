package com.osh.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity // Указываем Спрингу, что это сущность, а не обычный POJO Bean.
@Table
public class Employee {
    @Id
    @JsonView(Views.UserRoleView.class)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @JsonView(Views.UserRoleView.class)
    private String firstName;

    @NotNull
    @JsonView(Views.UserRoleView.class)
    private String lastName;

    @NotNull
    private int age;

    @NotNull
    @Column(unique = true)
    private String passportNumber;

    @NotNull
    @Column(unique = true)
    private String phoneNumber;

    @NotNull
    private String address;

    @JsonView(Views.UserRoleView.class)
    private int companyId;

    @JsonView(Views.UserRoleView.class)
    private int departmentId;

    @NotNull
    @JsonView(Views.UserRoleView.class)
    private String position;


    @Column(unique = true)
    private String userName;


    @Column(unique = true)
    private String password;

    @NotNull
    private String roles;

    @Column(updatable = false) // Аннотация нужна для того, чтобы поле не обновлялось
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime creationDate;

    public Employee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
