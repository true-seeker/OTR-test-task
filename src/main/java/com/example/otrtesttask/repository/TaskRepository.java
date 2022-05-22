package com.example.otrtesttask.repository;

import com.example.otrtesttask.dto.TaskDto;
import com.example.otrtesttask.jooq.Tables;
import com.example.otrtesttask.jooq.tables.pojos.Task;
import com.example.otrtesttask.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TaskRepository {
    @Autowired
    private final DSLContext dsl;
    private final MappingUtils mappingUtils;

    public TaskDto insert(Task task) {
        return dsl.insertInto(Tables.TASK)
                .set(dsl.newRecord(Tables.TASK, task))
                .returning()
                .fetchOneInto(TaskDto.class);
    }

    public TaskDto update(Integer id, Task task) {
        return dsl.update(Tables.TASK)
                .set(dsl.newRecord(Tables.TASK, task))
                .where(Tables.TASK.ID.eq(id))
                .returning()
                .fetchOneInto(TaskDto.class);
    }

    public TaskDto find(Integer id) {
        return dsl.selectFrom(Tables.TASK)
                .where(Tables.TASK.ID.eq(id))
                .fetchOneInto(TaskDto.class);
    }

    public List<TaskDto> findAll(Condition condition, Integer pageSize, Integer pageNumber) {
        return dsl.selectFrom(Tables.TASK)
                .where(condition)
                .orderBy(Tables.TASK.PRIORITY.desc(), Tables.TASK.ID)
                .limit(pageSize)
                .offset(pageNumber * pageSize)
                .fetchInto(Task.class)
                .stream().map(mappingUtils::mapToTaskDto)
                .collect(Collectors.toList());
    }

    public Boolean delete(Integer id) {
        return dsl.deleteFrom(Tables.TASK)
                .where(Tables.TASK.ID.eq(id))
                .execute() == 1;
    }

    public List<TaskDto> findTasksByEmployeeId(Integer id) {
        return dsl.selectFrom(Tables.TASK)
                .where(Tables.TASK.EMPLOYEE_ID.eq(id))
                .fetchInto(TaskDto.class);
    }
    public Integer getTotalItems(Condition condition) {
        return dsl.selectCount()
                .from(Tables.TASK)
                .where(condition)
                .fetchOneInto(Integer.class);
    }
}
