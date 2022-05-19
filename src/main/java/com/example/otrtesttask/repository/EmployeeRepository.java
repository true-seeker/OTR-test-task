package com.example.otrtesttask.repository;

import com.example.otrtesttask.dto.EmployeeDto;
import com.example.otrtesttask.jooq.Tables;
import com.example.otrtesttask.jooq.tables.pojos.Employee;
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
public class EmployeeRepository {
    @Autowired
    private final DSLContext dsl;
    private final MappingUtils mappingUtils;

    public Employee insert(Employee employee) {
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

    public List<EmployeeDto> findAll(Condition condition) {
        return dsl.selectFrom(Tables.EMPLOYEE)
                .where(condition)
                .orderBy(Tables.EMPLOYEE.ID)
                .fetchInto(Employee.class)
                .stream().map(mappingUtils::mapToEmployeeDto)
                .collect(Collectors.toList());
    }

    public Boolean delete(Integer id) {
        return dsl.deleteFrom(Tables.EMPLOYEE)
                .where(Tables.EMPLOYEE.ID.eq(id))
                .execute() == 1;
    }

    public List<Employee> findSubordinates(Integer id) {
        return dsl.selectFrom(Tables.EMPLOYEE)
                .where(Tables.EMPLOYEE.MANAGER_ID.eq(id))
                .fetchInto(Employee.class);
    }

    public List<Employee> findEmployeesByBranchId(Integer id) {
        return dsl.selectFrom(Tables.EMPLOYEE)
                .where(Tables.EMPLOYEE.BRANCH_ID.eq(id))
                .fetchInto(Employee.class);
    }

    public List<Employee> findEmployeesByPositionId(Integer id) {
        return dsl.selectFrom(Tables.EMPLOYEE)
                .where(Tables.EMPLOYEE.POSITION_ID.eq(id))
                .fetchInto(Employee.class);
    }

}