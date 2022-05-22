package com.example.otrtesttask.dto;

import lombok.Data;

import java.util.List;
@Data
public class TaskResponseDto {
    private Integer totalItems;
    private Integer currentPage;
    private List<TaskDto> taskDtoList;
}
