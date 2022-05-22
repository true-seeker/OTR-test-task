package com.example.otrtesttask.Entities.Employee;

import com.example.otrtesttask.jooq.Tables;
import com.example.otrtesttask.jooq.tables.pojos.Employee;
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
    private final EmployeeMapper employeeMapper;

    public EmployeeDto insert(Employee employee) {
        return dsl.insertInto(Tables.EMPLOYEE)
                .set(dsl.newRecord(Tables.EMPLOYEE, employee))
                .returning()
                .fetchOneInto(EmployeeDto.class);
    }

    public EmployeeDto update(Integer id, Employee employee) {
        return dsl.update(Tables.EMPLOYEE)
                .set(dsl.newRecord(Tables.EMPLOYEE, employee))
                .where(Tables.EMPLOYEE.ID.eq(id))
                .returning()
                .fetchOneInto(EmployeeDto.class);
    }

    public EmployeeDto find(Integer id) {
        return dsl.selectFrom(Tables.EMPLOYEE)
                .where(Tables.EMPLOYEE.ID.eq(id))
                .fetchOneInto(EmployeeDto.class);
    }

    public List<EmployeeDto> findAll(Condition condition, Integer pageSize, Integer pageNumber) {
        return dsl.selectFrom(Tables.EMPLOYEE)
                .where(condition)
                .orderBy(Tables.EMPLOYEE.ID)
                .limit(pageSize)
                .offset(pageNumber * pageSize)
                .fetchInto(Employee.class)
                .stream().map(employeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());
    }

    public Boolean delete(Integer id) {
        return dsl.deleteFrom(Tables.EMPLOYEE)
                .where(Tables.EMPLOYEE.ID.eq(id))
                .execute() == 1;
    }

    public List<EmployeeDto> findSubordinatesByManagerId(Integer id) {
        return dsl.selectFrom(Tables.EMPLOYEE)
                .where(Tables.EMPLOYEE.MANAGER_ID.eq(id))
                .fetchInto(EmployeeDto.class);
    }

    public List<EmployeeDto> findEmployeesByBranchId(Integer id) {
        return dsl.selectFrom(Tables.EMPLOYEE)
                .where(Tables.EMPLOYEE.BRANCH_ID.eq(id))
                .fetchInto(EmployeeDto.class);
    }

    public List<EmployeeDto> findEmployeesByPositionId(Integer id) {
        return dsl.selectFrom(Tables.EMPLOYEE)
                .where(Tables.EMPLOYEE.POSITION_ID.eq(id))
                .fetchInto(EmployeeDto.class);
    }

    public Integer getTotalItems(Condition condition) {
        return dsl.selectCount()
                .from(Tables.EMPLOYEE)
                .where(condition)
                .fetchOneInto(Integer.class);
    }

}