package com.example.otrtesttask.controller;

import com.example.otrtesttask.dto.TaskDto;
import com.example.otrtesttask.exceptions.CustomApiException;
import com.example.otrtesttask.jooq.tables.pojos.Task;
import com.example.otrtesttask.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/")
    public ResponseEntity<Task> createTask(@RequestBody Task task) throws CustomApiException {
//        Добавление задачи
        Task t = taskService.create(task);
        return ResponseEntity.ok(t);
    }

    @GetMapping("/")
    public ResponseEntity<List<TaskDto>> getTasks(@RequestParam(required = false) Short priority,
                                                  @RequestParam(required = false) String description,
                                                  @RequestParam(required = false) Integer employeeId) {
//        Получение списка задач
//        Возможен фильтр по полю priority, description, employeeId
        TaskDto taskDto = new TaskDto();
        taskDto.setPriority(priority);
        taskDto.setEmployeeId(employeeId);
        taskDto.setDescription(description);

        List<TaskDto> taskList = taskService.getTasks(taskDto);
        return ResponseEntity.ok(taskList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable(value = "id") Integer id) throws CustomApiException {
//        Получение задачи по идентификатору
        Task t = taskService.getTask(id);
        return ResponseEntity.ok(t);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable(value = "id") Integer id,
                                           @RequestBody Task task) throws CustomApiException {
//        Изменение информации о задаче
        Task t = taskService.update(id, task);
        return ResponseEntity.ok(t);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable(value = "id") Integer id) throws CustomApiException {
//        Удаление задачи по идентификатору
        Boolean b = taskService.delete(id);
        if (b)
            return ResponseEntity.ok("Ok");
        else
            return ResponseEntity.badRequest().body("Error");
    }

    @PostMapping("/{id}/setPriority")
    public ResponseEntity<Task> setPriority(@PathVariable(value = "id") Integer id,
                                            @RequestParam(required = true) Short newPriority) throws CustomApiException {
//        Изменение приоритета задачи
        Task t = taskService.setPriority(id, newPriority);
        return ResponseEntity.ok(t);
    }
}
