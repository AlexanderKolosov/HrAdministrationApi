package com.osh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osh.domain.Company;
import com.osh.repository.CompanyRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static Company company;

    private static String companyName;

    private static int companyId = 0;

    @Test
    @Transactional
    public void getListOfCompanies() throws Exception {
        this.mockMvc.perform(get("/companies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    @Transactional
    public void createCompany() throws Exception {
        companyName = "TestCompany";
        company = new Company();
        company.setName(companyName);

        this.mockMvc.perform(post("/companies")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(company))
                .with((user("user")
                        .authorities(new SimpleGrantedAuthority("ADMIN")))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    @Transactional
    public void getCompanyById() throws Exception {
        companyName = "TestCompany";
        company = new Company();
        company.setName(companyName);
        company.setCreationDate(LocalDateTime.now());

        companyRepository.save(company);

        Optional<Company> optionalCompany = companyRepository.findByName(companyName);
        company = optionalCompany.get();
        companyId = company.getId();

        this.mockMvc.perform(get("/companies/" + companyId)
                .with((user("user")
                        .authorities(new SimpleGrantedAuthority("ADMIN")))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    @Transactional
    public void editCompany() throws Exception {
        companyName = "TestCompany";
        company = new Company();
        company.setName(companyName);
        company.setCreationDate(LocalDateTime.now());

        companyRepository.save(company);

        Optional<Company> optionalCompany = companyRepository.findByName(companyName);
        company = optionalCompany.get();
        companyId = company.getId();

        companyName = "EditedTestCompany";

        this.mockMvc.perform(put("/companies/" + companyId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new Company(companyName)))
                .with((user("user")
                        .authorities(new SimpleGrantedAuthority("ADMIN")))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void deleteCompany() throws Exception {
        companyName = "TestCompany";
        company = new Company();
        company.setName(companyName);
        company.setCreationDate(LocalDateTime.now());

        companyRepository.save(company);

        Optional<Company> optionalCompany = companyRepository.findByName(companyName);
        company = optionalCompany.get();
        companyId = company.getId();

        companyName = null;

        this.mockMvc.perform(delete("/companies/" + companyId)
                .with((user("user")
                .authorities(new SimpleGrantedAuthority("ADMIN")))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Company TestCompany deleted successfully."));
    }

    @After
    @Transactional
    public void getCreatedCompany() {
        if (companyName != null) {
            Optional<Company> optionalCompany = companyRepository.findByName(companyName);

            company = optionalCompany.get();
            companyRepository.delete(company);
            companyName = null;
        }
    }
}