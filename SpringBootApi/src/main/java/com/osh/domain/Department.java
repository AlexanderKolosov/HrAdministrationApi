package com.osh.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity // Указываем Спрингу, что это сущность, а не обычный POJO Bean.
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "companyId"}))
public class Department {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.UserRoleView.class)
    private int id;

    @NotNull
    @JsonView(Views.UserRoleView.class)
    private String name;

    @NotNull
    @JsonView(Views.UserRoleView.class)
    private int companyId;

    @Column(updatable = false) // Аннотация нужна для того, чтобы поле не обновлялось
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDateTime creationDate;

    public Department() {
    }

    public Department(String name, int companyId) {
        this.name = name;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
