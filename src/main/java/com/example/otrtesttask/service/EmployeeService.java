package com.example.otrtesttask.service;

import com.example.otrtesttask.dto.EmployeeDto;
import com.example.otrtesttask.jooq.tables.pojos.Employee;
import com.example.otrtesttask.repository.EmployeeRepository;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee create(Employee employee) {
        return employeeRepository.insert(employee);
    }

    public List<EmployeeDto> getEmployees(Condition condition) {

        return employeeRepository.findAll(condition);
    }

    public Employee getEmployee(Integer id) {

        return employeeRepository.find(id);
    }

    public Boolean delete(Integer employeeId) {
        return employeeRepository.delete(employeeId);
    }

    public Employee update(Integer id, Employee employee) {
        return employeeRepository.update(id, employee);
    }

}
