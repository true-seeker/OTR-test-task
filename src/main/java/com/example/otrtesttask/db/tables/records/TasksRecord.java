/*
 * This file is generated by jOOQ.
 */
package com.example.otrtesttask.db.tables.records;


import com.example.otrtesttask.db.tables.Tasks;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TasksRecord extends UpdatableRecordImpl<TasksRecord> implements Record4<Integer, Short, String, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.tasks.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.tasks.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.tasks.priority</code>.
     */
    public void setPriority(Short value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.tasks.priority</code>.
     */
    public Short getPriority() {
        return (Short) get(1);
    }

    /**
     * Setter for <code>public.tasks.description</code>.
     */
    public void setDescription(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.tasks.description</code>.
     */
    public String getDescription() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.tasks.employee_id</code>.
     */
    public void setEmployeeId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.tasks.employee_id</code>.
     */
    public Integer getEmployeeId() {
        return (Integer) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, Short, String, Integer> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Integer, Short, String, Integer> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Tasks.TASKS.ID;
    }

    @Override
    public Field<Short> field2() {
        return Tasks.TASKS.PRIORITY;
    }

    @Override
    public Field<String> field3() {
        return Tasks.TASKS.DESCRIPTION;
    }

    @Override
    public Field<Integer> field4() {
        return Tasks.TASKS.EMPLOYEE_ID;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Short component2() {
        return getPriority();
    }

    @Override
    public String component3() {
        return getDescription();
    }

    @Override
    public Integer component4() {
        return getEmployeeId();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Short value2() {
        return getPriority();
    }

    @Override
    public String value3() {
        return getDescription();
    }

    @Override
    public Integer value4() {
        return getEmployeeId();
    }

    @Override
    public TasksRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public TasksRecord value2(Short value) {
        setPriority(value);
        return this;
    }

    @Override
    public TasksRecord value3(String value) {
        setDescription(value);
        return this;
    }

    @Override
    public TasksRecord value4(Integer value) {
        setEmployeeId(value);
        return this;
    }

    @Override
    public TasksRecord values(Integer value1, Short value2, String value3, Integer value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TasksRecord
     */
    public TasksRecord() {
        super(Tasks.TASKS);
    }

    /**
     * Create a detached, initialised TasksRecord
     */
    public TasksRecord(Integer id, Short priority, String description, Integer employeeId) {
        super(Tasks.TASKS);

        setId(id);
        setPriority(priority);
        setDescription(description);
        setEmployeeId(employeeId);
    }
}
