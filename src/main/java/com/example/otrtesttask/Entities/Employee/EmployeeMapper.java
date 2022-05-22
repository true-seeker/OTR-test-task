package com.example.otrtesttask.Entities.Employee;

import com.example.otrtesttask.jooq.tables.daos.BranchDao;
import com.example.otrtesttask.jooq.tables.daos.EmployeeDao;
import com.example.otrtesttask.jooq.tables.daos.PositionDao;
import com.example.otrtesttask.jooq.tables.daos.TaskDao;
import com.example.otrtesttask.jooq.tables.pojos.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeMapper {
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    PositionDao positionDao;
    @Autowired
    BranchDao branchDao;
    @Autowired
    TaskDao taskDao;

    // List<EmployeeDto> -> EmployeeResponseDto
    public EmployeeResponseDto mapToEmployeeResponseDto(List<EmployeeDto> employeeDtoList, Integer currentPage, Integer totalItems) {
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
        employeeResponseDto.setEmployeeDtoList(employeeDtoList);
        employeeResponseDto.setCurrentPage(currentPage);
        employeeResponseDto.setTotalItems(totalItems);
        return employeeResponseDto;
    }


    // Employee -> EmployeeDto
    public EmployeeDto mapToEmployeeDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();

        dto.setId(employee.getId());
        dto.setBranchId(employee.getBranchId());
        dto.setPositionId(employee.getPositionId());
        dto.setFullName(employee.getFullName());
        dto.setManagerId(employee.getManagerId());
        dto.setManager(employeeDao.fetchOneById(employee.getManagerId()));
        dto.setPosition(positionDao.fetchOneById(employee.getPositionId()));
        dto.setBranch(branchDao.fetchOneById(employee.getBranchId()));
        dto.setTaskCount(taskDao.fetchByEmployeeId(employee.getId()).size());
        return dto;
    }

    // EmployeeDto -> Employee
    public Employee mapToEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setBranchId(employeeDto.getBranchId());
        employee.setManagerId(employeeDto.getManagerId());
        employee.setFullName(employeeDto.getFullName());
        employee.setPositionId(employeeDto.getPositionId());
        return employee;
    }
}
