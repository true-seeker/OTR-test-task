package com.example.otrtesttask.service;

import com.example.otrtesttask.dto.TaskDto;
import com.example.otrtesttask.jooq.tables.pojos.Task;
import com.example.otrtesttask.repository.TaskRepository;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task create(Task task) {
        return taskRepository.insert(task);
    }

    public Task update(Integer id, Task task) {
        return taskRepository.update(id, task);
    }

    public Task getTask(Integer id) {
        return taskRepository.find(id);
    }

    public List<TaskDto> getTasks(Condition condition) {
        return taskRepository.findAll(condition);
    }

    public Boolean delete(Integer id) {
        return taskRepository.delete(id);
    }
}
