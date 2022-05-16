package com.example.otrtesttask.controller;

import com.example.otrtesttask.dto.TaskDto;
import com.example.otrtesttask.jooq.Tables;
import com.example.otrtesttask.jooq.tables.pojos.Task;
import com.example.otrtesttask.service.TaskService;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.jooq.impl.DSL.trueCondition;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/")
    public ResponseEntity<Object> createTask(@RequestBody Task task) {
        Task t = taskService.create(task);
        return ResponseEntity.ok(t);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getTasks(@RequestParam(required = false) String title) {
        Condition condition = trueCondition();

        if (title != null)
            condition = condition.and(Tables.BRANCH.TITLE.containsIgnoreCase(title));
        List<TaskDto> taskList = taskService.getTasks(condition);
        return ResponseEntity.ok(taskList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTask(@PathVariable(value = "id") Integer id) {
        Task t = taskService.getTask(id);
        return ResponseEntity.ok(t);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable(value = "id") Integer id,
                                             @RequestBody Task task) {
        Task t = taskService.update(id, task);
        return ResponseEntity.ok(t);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable(value = "id") Integer id) {
        Boolean b = taskService.delete(id);
        if (b)
            return ResponseEntity.ok("Ok");
        else
            return ResponseEntity.badRequest().body("Error");
    }
}
