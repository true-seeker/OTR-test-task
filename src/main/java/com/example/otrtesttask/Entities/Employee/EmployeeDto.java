package com.example.otrtesttask.Entities.Employee;

import com.example.otrtesttask.jooq.tables.pojos.Branch;
import com.example.otrtesttask.jooq.tables.pojos.Position;
import com.example.otrtesttask.jooq.tables.pojos.Employee;
import lombok.Data;

@Data
public class EmployeeDto {
    // DTO для Employee
    private Integer id;
    private Integer managerId;
    private Integer positionId;
    private String fullName;
    private Integer branchId;

    // Начальник
    private Employee manager;

    // Должность
    private Position position;

    // Подразделение
    private Branch branch;

    // Количество задач
    private Integer taskCount;
}
