package com.osh.service;

import com.osh.domain.Employee;
import com.osh.domain.EmployeeDetails;
import com.osh.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
        Optional<Employee> optionalEmployee = employeeRepository.findByUserName(userName);

        optionalEmployee.orElseThrow(() -> new UsernameNotFoundException("Employee with login " + userName + " not found."));

        return optionalEmployee.map(EmployeeDetails::new).get();
    }
}
