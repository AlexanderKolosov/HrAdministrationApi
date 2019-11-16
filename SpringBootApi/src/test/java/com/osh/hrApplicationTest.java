package com.osh;

import com.osh.controller.CompanyController;
import com.osh.controller.DepartmentController;
import com.osh.controller.EmployeeController;
import com.osh.controller.VacationController;
import com.osh.domain.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class hrApplicationTest {
    @Autowired
    private CompanyController companyController;

    @Autowired
    private DepartmentController departmentController;

    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private VacationController vacationController;

    @Test
    public void companyContextLoads() throws Exception {
        assertThat(companyController).isNotNull();
    }

    @Test
    public void departmentContextLoads() throws Exception {
        assertThat(departmentController).isNotNull();
    }

    @Test
    public void employeeContextLoads() throws Exception {
        assertThat(employeeController).isNotNull();
    }

    @Test
    public void vacationContextLoads() throws Exception {
        assertThat(vacationController).isNotNull();
    }
}