package com.example.otrtesttask.utils;

import com.example.otrtesttask.dto.EmployeeDto;
import com.example.otrtesttask.dto.TaskDto;
import com.example.otrtesttask.jooq.tables.daos.BranchDao;
import com.example.otrtesttask.jooq.tables.daos.EmployeeDao;
import com.example.otrtesttask.jooq.tables.daos.PositionDao;
import com.example.otrtesttask.jooq.tables.daos.TaskDao;
import com.example.otrtesttask.jooq.tables.pojos.Employee;
import com.example.otrtesttask.jooq.tables.pojos.Task;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    PositionDao positionDao;
    @Autowired
    BranchDao branchDao;
    @Autowired
    TaskDao taskDao;

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

    // Task -> TaskDto
    public TaskDto mapToTaskDto(Task task) {
        TaskDto dto = new TaskDto();

        dto.setId(task.getId());
        dto.setDescription(task.getDescription());
        dto.setPriority(task.getPriority());
        dto.setEmployeeId(task.getEmployeeId());
        dto.setEmployee(employeeDao.fetchOneById(task.getEmployeeId()));
        return dto;
    }

    // TaskDto -> Task
    public Task mapToTask(TaskDto taskDto) {
        Task task = new Task();
        task.setDescription(taskDto.getDescription());
        task.setId(taskDto.getId());
        task.setPriority(taskDto.getPriority());
        task.setEmployeeId(taskDto.getEmployeeId());
        return task;
    }
}
