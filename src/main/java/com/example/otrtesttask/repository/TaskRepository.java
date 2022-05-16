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

    public Task insert(Task task) {
        return dsl.insertInto(Tables.TASK)
                .set(dsl.newRecord(Tables.TASK, task))
                .returning()
                .fetchOneInto(Task.class);
    }

    public Task update(Integer id, Task task) {
        return dsl.update(Tables.TASK)
                .set(dsl.newRecord(Tables.TASK, task))
                .where(Tables.TASK.ID.eq(id))
                .returning()
                .fetchOneInto(Task.class);
    }

    public Task find(Integer id) {
        return dsl.selectFrom(Tables.TASK)
                .where(Tables.TASK.ID.eq(id))
                .fetchOneInto(Task.class);
    }

    public List<TaskDto> findAll(Condition condition) {
        return dsl.selectFrom(Tables.TASK)
                .where(condition)
                .orderBy(Tables.TASK.PRIORITY.desc(), Tables.TASK.ID)
                .fetchInto(Task.class)
                .stream().map(mappingUtils::mapToTaskTdo)
                .collect(Collectors.toList());
    }

    public Boolean delete(Integer id) {
        return dsl.deleteFrom(Tables.TASK)
                .where(Tables.TASK.ID.eq(id))
                .execute() == 1;
    }

    public List<Task> findTasksByEmployeeId(Integer id) {
        return dsl.selectFrom(Tables.TASK)
                .where(Tables.TASK.EMPLOYEE_ID.eq(id))
                .fetchInto(Task.class);
    }
}
