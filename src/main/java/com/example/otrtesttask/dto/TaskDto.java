package com.example.otrtesttask.dto;

import com.example.otrtesttask.jooq.tables.pojos.Employee;
import lombok.Data;

@Data
public class TaskDto {
    private Integer id;
    private Short priority;
    private String description;
    private Integer employeeId;
    private Employee employee;
}
