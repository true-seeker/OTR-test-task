package com.example.otrtesttask.Entities.Task;

import com.example.otrtesttask.jooq.tables.daos.EmployeeDao;
import com.example.otrtesttask.jooq.tables.pojos.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskMapper {
    @Autowired
    EmployeeDao employeeDao;

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

    // List<TaskDto> -> TaskResponseDto
    public TaskResponseDto mapToTaskResponseDto(List<TaskDto> taskDtoList, Integer currentPage, Integer totalItems) {
        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setTaskDtoList(taskDtoList);
        taskResponseDto.setCurrentPage(currentPage);
        taskResponseDto.setTotalItems(totalItems);
        return taskResponseDto;
    }
}
