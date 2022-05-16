package com.example.otrtesttask.service;

import com.example.otrtesttask.dto.TaskDto;
import com.example.otrtesttask.exceptions.CustomApiException;
import com.example.otrtesttask.jooq.tables.pojos.Task;
import com.example.otrtesttask.repository.TaskRepository;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    private final Short minPriority = 1;
    private final Short maxPriority = 10;

    public Task create(Task task) throws CustomApiException {
        if (task.getEmployeeId() == null)
            throw new CustomApiException("Missing required field: employeeId", HttpStatus.BAD_REQUEST);
        if (task.getDescription() == null)
            throw new CustomApiException("Missing required field: description", HttpStatus.BAD_REQUEST);
        if (task.getPriority() == null)
            throw new CustomApiException("Missing required field: priority", HttpStatus.BAD_REQUEST);
        return taskRepository.insert(task);
    }

    public Task update(Integer id, Task task) throws CustomApiException {
        Task t = taskRepository.update(id, task);
        if (t == null)
            throw new CustomApiException(String.format("Task with id %d not found", id), HttpStatus.NOT_FOUND);
        return t;
    }

    public Task getTask(Integer id) throws CustomApiException {
        Task t = taskRepository.find(id);
        if (t == null)
            throw new CustomApiException(String.format("Task with id %d not found", id), HttpStatus.NOT_FOUND);
        return t;
    }

    public List<TaskDto> getTasks(Condition condition) {
        return taskRepository.findAll(condition);
    }

    public Boolean delete(Integer id) throws CustomApiException {
        Boolean b = taskRepository.delete(id);
        if (!b)
            throw new CustomApiException(String.format("Task with id %d not found", id), HttpStatus.NOT_FOUND);
        return b;
    }

    public Task setPriority(Integer id, Short newPriority) throws CustomApiException {
        if (newPriority < minPriority || newPriority > maxPriority)
            throw new CustomApiException("Priority must be in range [1;10]", HttpStatus.BAD_REQUEST);

        Task task = taskRepository.find(id);
        if (task == null)
            throw new CustomApiException(String.format("Task with id %d not found", id), HttpStatus.NOT_FOUND);
        task.setPriority(newPriority);
        return taskRepository.update(id, task);
    }
}
