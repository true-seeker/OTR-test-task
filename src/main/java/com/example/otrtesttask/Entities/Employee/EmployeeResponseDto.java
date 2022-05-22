package com.example.otrtesttask.Entities.Employee;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeResponseDto {
    private Integer totalItems;
    private Integer currentPage;
    private List<EmployeeDto> employeeDtoList;
}
