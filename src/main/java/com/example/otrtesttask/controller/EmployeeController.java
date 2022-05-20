package com.example.otrtesttask.controller;

import com.example.otrtesttask.dto.EmployeeDto;
import com.example.otrtesttask.exceptions.CustomApiException;
import com.example.otrtesttask.jooq.tables.pojos.Employee;
import com.example.otrtesttask.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) throws CustomApiException {
        //Добавление сотрудника
        Employee e = employeeService.create(employee);
        return ResponseEntity.ok(e);
    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeDto>> getEmployees(@RequestParam(required = false) Integer managerId,
                                                          @RequestParam(required = false) String fullName,
                                                          @RequestParam(required = false) Integer positionId,
                                                          @RequestParam(required = false) Integer branchId) {
//        Получение списка всех работников
//        Возможен фильтр по полям:, managerId, fullName, positionId, branchId
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setBranchId(branchId);
        employeeDto.setFullName(fullName);
        employeeDto.setManagerId(managerId);
        employeeDto.setPositionId(positionId);

        List<EmployeeDto> employeeList = employeeService.getEmployees(employeeDto);
        return ResponseEntity.ok(employeeList);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable(value = "employeeId") Integer id) throws CustomApiException {
//        Получение сотрудника по его идентификатору
        Employee e = employeeService.getEmployee(id);
        return ResponseEntity.ok(e);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "employeeId") Integer id,
                                                   @RequestBody Employee employee) throws CustomApiException {
//        Обновление информации о сотруднике
        Employee e = employeeService.update(id, employee);
        return ResponseEntity.ok(e);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable(value = "employeeId") Integer id) throws CustomApiException {
//        Удаление сотрудника по идентификатору
        Boolean b = employeeService.delete(id);
        if (b)
            return ResponseEntity.ok("Employee was deleted successfully");
        else
            return ResponseEntity.badRequest().body("Error");
    }
}