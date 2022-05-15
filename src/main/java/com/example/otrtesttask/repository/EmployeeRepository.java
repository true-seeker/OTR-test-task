package com.example.otrtesttask.repository;

import com.example.otrtesttask.jooq.Tables;
import com.example.otrtesttask.jooq.tables.pojos.Employee;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class EmployeeRepository {
    @Autowired
    private final DSLContext dsl;

    public Employee insert(Employee employee) {
        System.out.println(employee);
        return dsl.insertInto(Tables.EMPLOYEE)
                .set(dsl.newRecord(Tables.EMPLOYEE, employee))
                .returning()
                .fetchOneInto(Employee.class);
    }

    public Employee update(Integer id, Employee employee) {
        return dsl.update(Tables.EMPLOYEE)
                .set(dsl.newRecord(Tables.EMPLOYEE, employee))
                .where(Tables.EMPLOYEE.ID.eq(id))
                .returning()
                .fetchOneInto(Employee.class);
    }

    public Employee find(Integer id) {
        return dsl.selectFrom(Tables.EMPLOYEE)
                .where(Tables.EMPLOYEE.ID.eq(id))
                .fetchOneInto(Employee.class);
    }

    public List<Employee> findAll(Condition condition) {
        return dsl.selectFrom(Tables.EMPLOYEE)
                .where(condition)
                .fetchInto(Employee.class);
    }

    public Boolean delete(Integer id) {
        return dsl.deleteFrom(Tables.EMPLOYEE)
                .where(Tables.EMPLOYEE.ID.eq(id))
                .execute() == 1;
    }

}