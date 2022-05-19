package com.example.otrtesttask.rest;

import com.example.otrtesttask.controller.EmployeeController;
import com.example.otrtesttask.exceptions.CustomApiException;
import com.example.otrtesttask.jooq.tables.pojos.Employee;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class ControllerTest {
    @Autowired
    EmployeeController employeeController;

    @Test
    public void getEmployee() throws CustomApiException {
        Integer id = 1;
        ResponseEntity<Employee> responseEntity = employeeController.getEmployee(id);
        MatcherAssert.assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    @Transactional
    public void insertNewEmployee() throws CustomApiException {
        Employee newEmployee = new Employee();
        newEmployee.setFullName("test");
        newEmployee.setManagerId(1);
        newEmployee.setBranchId(1);
        newEmployee.setPositionId(1);

        ResponseEntity<Employee> responseEntity = employeeController.createEmployee(newEmployee);

        MatcherAssert.assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    @Transactional
    public void updateEmployee() throws CustomApiException {
        Employee newEmployee = new Employee();
        newEmployee.setFullName("test");
        newEmployee.setManagerId(1);
        newEmployee.setBranchId(1);
        newEmployee.setPositionId(1);

        newEmployee = employeeController.createEmployee(newEmployee).getBody();

        ResponseEntity<Employee> responseEntity = employeeController.updateEmployee(newEmployee.getId(), newEmployee);

        MatcherAssert.assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    @Transactional
    public void deleteEmployee() throws CustomApiException {
        Employee newEmployee = new Employee();
        newEmployee.setFullName("test");
        newEmployee.setManagerId(1);
        newEmployee.setBranchId(1);
        newEmployee.setPositionId(1);

        newEmployee = employeeController.createEmployee(newEmployee).getBody();

        ResponseEntity<Object> responseEntity = employeeController.deleteEmployee(newEmployee.getId());

        MatcherAssert.assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
    }
}
