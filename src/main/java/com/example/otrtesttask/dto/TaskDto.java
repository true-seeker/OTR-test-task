package com.example.otrtesttask.dto;

import com.example.otrtesttask.jooq.tables.pojos.Employee;
import lombok.Data;

@Data
public class TaskDto {
    // DTO для класса Task
    private Integer id;
    private Short priority;
    private String description;
    private Integer employeeId;

    // Поле информации о сотруднике
    private Employee employee;
}
