package com.example.otrtesttask.controller;

import com.example.otrtesttask.dto.EmployeeDto;
import com.example.otrtesttask.dto.EmployeeResponseDto;
import com.example.otrtesttask.exceptions.CustomApiException;
import com.example.otrtesttask.jooq.tables.pojos.Employee;
import com.example.otrtesttask.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody Employee employee) throws CustomApiException {
        //Добавление сотрудника
        EmployeeDto e = employeeService.create(employee);
        return ResponseEntity.ok(e);
    }

    @GetMapping("/")
    public ResponseEntity<EmployeeResponseDto> getEmployees(@RequestParam(required = false) Integer managerId,
                                                            @RequestParam(required = false) String fullName,
                                                            @RequestParam(required = false) Integer positionId,
                                                            @RequestParam(required = false) Integer branchId,
                                                            @RequestParam(required = false, defaultValue = "50") Integer pageSize,
                                                            @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {
//        Получение списка всех работников
//        Возможен фильтр по полям:, managerId, fullName, positionId, branchId
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setBranchId(branchId);
        employeeDto.setFullName(fullName);
        employeeDto.setManagerId(managerId);
        employeeDto.setPositionId(positionId);

        EmployeeResponseDto employeeResponseDto = employeeService.getEmployees(employeeDto, pageSize, pageNumber);
        return ResponseEntity.ok(employeeResponseDto);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable(value = "employeeId") Integer id) throws CustomApiException {
//        Получение сотрудника по его идентификатору
        EmployeeDto e = employeeService.getEmployee(id);
        return ResponseEntity.ok(e);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable(value = "employeeId") Integer id,
                                                      @RequestBody Employee employee) throws CustomApiException {
//        Обновление информации о сотруднике
        EmployeeDto e = employeeService.update(id, employee);
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