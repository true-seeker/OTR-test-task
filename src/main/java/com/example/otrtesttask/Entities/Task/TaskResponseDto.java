package com.example.otrtesttask.Entities.Task;

import lombok.Data;

import java.util.List;

@Data
public class TaskResponseDto {
    private Integer totalItems;
    private Integer currentPage;
    private List<TaskDto> taskDtoList;
}
