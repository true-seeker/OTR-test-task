package com.example.otrtesttask.dto;

import com.example.otrtesttask.jooq.tables.pojos.Branch;
import com.example.otrtesttask.jooq.tables.pojos.Position;
import com.example.otrtesttask.jooq.tables.pojos.Employee;
import lombok.Data;

@Data
public class EmployeeDto {
    private Integer id;
    private Integer managerId;
    private Integer positionId;
    private String fullName;
    private Integer branchId;
    private Employee manager;
    private Position position;
    private Branch branch;
    private Integer taskCount;
}
