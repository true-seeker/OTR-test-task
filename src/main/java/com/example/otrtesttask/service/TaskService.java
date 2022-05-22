package com.example.otrtesttask.service;

import com.example.otrtesttask.dto.EmployeeDto;
import com.example.otrtesttask.dto.TaskDto;
import com.example.otrtesttask.dto.TaskResponseDto;
import com.example.otrtesttask.exceptions.CustomApiException;
import com.example.otrtesttask.jooq.Tables;
import com.example.otrtesttask.jooq.tables.pojos.Task;
import com.example.otrtesttask.repository.EmployeeRepository;
import com.example.otrtesttask.repository.TaskRepository;
import com.example.otrtesttask.utils.MappingUtils;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static org.jooq.impl.DSL.trueCondition;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    private final Short minPriority = 1;
    private final Short maxPriority = 10;
    private final Integer defaultPageSize = 50;

    private final MappingUtils mappingUtils = new MappingUtils();

    public TaskDto create(Task task) throws CustomApiException {
        // Если передали id, то возвращаем ошибку
        if (task.getId() != null)
            throw new CustomApiException("Id field is prohibited", HttpStatus.BAD_REQUEST);
        // В запросе не передан employeeId
        if (task.getEmployeeId() == null)
            throw new CustomApiException("Missing required field: employeeId", HttpStatus.BAD_REQUEST);
        // В запросе не передан description
        if (task.getDescription() == null)
            throw new CustomApiException("Missing required field: description", HttpStatus.BAD_REQUEST);
        // В запросе не передан priority
        if (task.getPriority() == null)
            throw new CustomApiException("Missing required field: priority", HttpStatus.BAD_REQUEST);
        // Исполнителя не существует
        EmployeeDto e = employeeRepository.find(task.getEmployeeId());
        if (e == null)
            throw new CustomApiException(String.format("Employee with id %d not found", task.getEmployeeId()), HttpStatus.BAD_REQUEST);

        return taskRepository.insert(task);
    }

    public TaskDto update(Integer id, Task task) throws CustomApiException {
        // Исполнителя не существует
        EmployeeDto e = employeeRepository.find(task.getEmployeeId());
        if (e == null)
            throw new CustomApiException(String.format("Employee with id %d not found", task.getEmployeeId()), HttpStatus.BAD_REQUEST);

        TaskDto t = taskRepository.update(id, task);
        // Нет сущности с таким идентификатором
        if (t == null)
            throw new CustomApiException(String.format("Task with id %d not found", id), HttpStatus.NOT_FOUND);

        return t;
    }

    public TaskDto getTask(Integer id) throws CustomApiException {
        TaskDto t = taskRepository.find(id);
        // Нет сущности с таким идентификатором
        if (t == null)
            throw new CustomApiException(String.format("Task with id %d not found", id), HttpStatus.NOT_FOUND);

        return t;
    }

    public TaskResponseDto getTasks(TaskDto taskDto, Integer pageSize, Integer pageNumber) {
        if (pageSize > defaultPageSize)
            pageSize = defaultPageSize;

        Condition condition = trueCondition();

        if (taskDto.getDescription() != null)
            condition = condition.and(Tables.TASK.DESCRIPTION.containsIgnoreCase(taskDto.getDescription()));
        if (taskDto.getPriority() != null)
            condition = condition.and(Tables.TASK.PRIORITY.eq(taskDto.getPriority()));
        if (taskDto.getEmployeeId() != null)
            condition = condition.and(Tables.TASK.EMPLOYEE_ID.eq(taskDto.getEmployeeId()));

        return mappingUtils.mapToTaskResponseDto(taskRepository.findAll(condition, pageSize, pageNumber),
                pageNumber,
                taskRepository.getTotalItems(condition));
    }

    public Boolean delete(Integer id) throws CustomApiException {
        Boolean b = taskRepository.delete(id);
        // Нет сущности с таким идентификатором
        if (!b)
            throw new CustomApiException(String.format("Task with id %d not found", id), HttpStatus.NOT_FOUND);

        return b;
    }

    public TaskDto setPriority(Integer id, Short newPriority) throws CustomApiException {
        // Новое значение приоритета не удовлетворяет условиям
        if (newPriority < minPriority || newPriority > maxPriority)
            throw new CustomApiException(String.format("Priority must be in range [%d;%d]", minPriority, maxPriority), HttpStatus.BAD_REQUEST);

        TaskDto taskDto = taskRepository.find(id);
        // Нет сущности с таким идентификатором
        if (taskDto == null)
            throw new CustomApiException(String.format("Task with id %d not found", id), HttpStatus.NOT_FOUND);
        taskDto.setPriority(newPriority);

        return taskRepository.update(id, mappingUtils.mapToTask(taskDto));
    }


}
