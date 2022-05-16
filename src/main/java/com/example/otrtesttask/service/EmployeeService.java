package com.example.otrtesttask.service;

import com.example.otrtesttask.dto.EmployeeDto;
import com.example.otrtesttask.exceptions.CustomApiException;
import com.example.otrtesttask.jooq.tables.pojos.Employee;
import com.example.otrtesttask.repository.EmployeeRepository;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee create(Employee employee) throws CustomApiException {
        if (employee.getBranchId() == null)
            throw new CustomApiException("Missing required field: branchId", HttpStatus.BAD_REQUEST);
        if (employee.getPositionId() == null)
            throw new CustomApiException("Missing required field: positionId", HttpStatus.BAD_REQUEST);
        if (employee.getFullName() == null)
            throw new CustomApiException("Missing required field: fullName", HttpStatus.BAD_REQUEST);
        return employeeRepository.insert(employee);
    }

    public List<EmployeeDto> getEmployees(Condition condition) {

        return employeeRepository.findAll(condition);
    }

    public Employee getEmployee(Integer id) throws CustomApiException {

        Employee e = employeeRepository.find(id);
        if (e == null)
            throw new CustomApiException(String.format("Employee with id %d not found", id), HttpStatus.NOT_FOUND);
        return e;
    }

    public Boolean delete(Integer id) throws CustomApiException {
        Boolean b = employeeRepository.delete(id);
        if (!b)
            throw new CustomApiException(String.format("Employee with id %d not found", id), HttpStatus.NOT_FOUND);
        return b;
    }

    public Employee update(Integer id, Employee employee) throws CustomApiException {
        Employee e = employeeRepository.update(id, employee);
        if (e == null)
            throw new CustomApiException(String.format("Employee with id %d not found", id), HttpStatus.NOT_FOUND);
        return e;
    }

}
