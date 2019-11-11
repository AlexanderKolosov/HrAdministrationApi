package com.osh.service;

import com.osh.domain.Employee;
import com.osh.domain.EmployeeDetails;
import com.osh.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeDetailsService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepository.findByUserName(userName);

        employee.orElseThrow(() -> new UsernameNotFoundException("Employee with login " + userName + " not found."));

        return employee.map(EmployeeDetails::new).get();
    }
}
