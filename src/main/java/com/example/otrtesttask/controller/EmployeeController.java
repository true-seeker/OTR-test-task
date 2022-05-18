package com.example.otrtesttask.controller;

import com.example.otrtesttask.dto.EmployeeDto;
import com.example.otrtesttask.exceptions.CustomApiException;
import com.example.otrtesttask.jooq.Tables;
import com.example.otrtesttask.jooq.tables.pojos.Employee;
import com.example.otrtesttask.service.EmployeeService;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.jooq.impl.DSL.trueCondition;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) throws CustomApiException {
        Employee e = employeeService.create(employee);
        return ResponseEntity.ok(e);
    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeDto>> getEmployees(@RequestParam(required = false) Integer managerId,
                                               @RequestParam(required = false) String fullName,
                                               @RequestParam(required = false) Integer positionId,
                                               @RequestParam(required = false) Integer branchId) {

        Condition condition = trueCondition();
        if (managerId != null)
            condition = condition.and(Tables.EMPLOYEE.MANAGER_ID.eq(managerId));
        if (positionId != null)
            condition = condition.and(Tables.EMPLOYEE.POSITION_ID.eq(positionId));
        if (fullName != null)
            condition = condition.and(Tables.EMPLOYEE.FULL_NAME.containsIgnoreCase(fullName));
        if (branchId != null)
            condition = condition.and(Tables.EMPLOYEE.BRANCH_ID.eq(branchId));

        List<EmployeeDto> employeeList = employeeService.getEmployees(condition);
        return ResponseEntity.ok(employeeList);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable(value = "employeeId") Integer id) throws CustomApiException {
        Employee e = employeeService.getEmployee(id);
        return ResponseEntity.ok(e);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "employeeId") Integer id,
                                                 @RequestBody Employee employee) throws CustomApiException {
        Employee e = employeeService.update(id, employee);
        return ResponseEntity.ok(e);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable(value = "employeeId") Integer id) throws CustomApiException {
        Boolean b = employeeService.delete(id);
        if (b)
            return ResponseEntity.ok("Сотрудник успешно удалён");
        else
            return ResponseEntity.badRequest().body("Произошла ошибка");
    }
}