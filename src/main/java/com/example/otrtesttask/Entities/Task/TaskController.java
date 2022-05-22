package com.example.otrtesttask.Entities.Task;

import com.example.otrtesttask.Exceptions.CustomApiException;
import com.example.otrtesttask.jooq.tables.pojos.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/")
    public ResponseEntity<TaskDto> createTask(@RequestBody Task task) throws CustomApiException {
//        Добавление задачи
        TaskDto t = taskService.create(task);
        return ResponseEntity.ok(t);
    }

    @GetMapping("/")
    public ResponseEntity<TaskResponseDto> getTasks(@RequestParam(required = false) Short priority,
                                                    @RequestParam(required = false) String description,
                                                    @RequestParam(required = false) Integer employeeId,
                                                    @RequestParam(required = false, defaultValue = "50") Integer pageSize,
                                                    @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {

//        Получение списка задач
//        Возможен фильтр по полю priority, description, employeeId
        TaskDto taskDto = new TaskDto();
        taskDto.setPriority(priority);
        taskDto.setEmployeeId(employeeId);
        taskDto.setDescription(description);

        TaskResponseDto taskResponseDto = taskService.getTasks(taskDto, pageSize, pageNumber);
        return ResponseEntity.ok(taskResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable(value = "id") Integer id) throws CustomApiException {
//        Получение задачи по идентификатору
        TaskDto t = taskService.getTask(id);
        return ResponseEntity.ok(t);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable(value = "id") Integer id,
                                              @RequestBody Task task) throws CustomApiException {
//        Изменение информации о задаче
        TaskDto t = taskService.update(id, task);
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
    public ResponseEntity<TaskDto> setPriority(@PathVariable(value = "id") Integer id,
                                               @RequestParam(required = true) Short newPriority) throws CustomApiException {
//        Изменение приоритета задачи
        TaskDto t = taskService.setPriority(id, newPriority);
        return ResponseEntity.ok(t);
    }
}
