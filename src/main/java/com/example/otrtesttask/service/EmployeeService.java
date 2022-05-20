package com.example.otrtesttask.service;

import com.example.otrtesttask.dto.EmployeeDto;
import com.example.otrtesttask.exceptions.CustomApiException;
import com.example.otrtesttask.jooq.tables.pojos.Employee;
import com.example.otrtesttask.jooq.tables.pojos.Task;
import com.example.otrtesttask.repository.BranchRepository;
import com.example.otrtesttask.repository.EmployeeRepository;
import com.example.otrtesttask.repository.PositionRepository;
import com.example.otrtesttask.repository.TaskRepository;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private PositionRepository positionRepository;

    public Employee create(Employee employee) throws CustomApiException {
        // Если передали id, то возвращаем ошибку
        if (employee.getId() != null)
            throw new CustomApiException("Id field is prohibited", HttpStatus.BAD_REQUEST);
        // В запросе не передан branchId
        if (employee.getBranchId() == null)
            throw new CustomApiException("Missing required field: branchId", HttpStatus.BAD_REQUEST);
        // В запросе не передан positionId
        if (employee.getPositionId() == null)
            throw new CustomApiException("Missing required field: positionId", HttpStatus.BAD_REQUEST);
        // В запросе не передан fullName
        if (employee.getFullName() == null)
            throw new CustomApiException("Missing required field: fullName", HttpStatus.BAD_REQUEST);
        // Не существует должности
        if (positionRepository.find(employee.getPositionId()) == null)
            throw new CustomApiException(String.format("Position with id %d not found", employee.getPositionId()), HttpStatus.BAD_REQUEST);
        // Не существует подразделения
        if (branchRepository.find(employee.getBranchId()) == null)
            throw new CustomApiException(String.format("Branch with id %d not found", employee.getBranchId()), HttpStatus.BAD_REQUEST);
        // Начальник не существует
        if (employeeRepository.find(employee.getManagerId()) == null)
            throw new CustomApiException(String.format("Manager with id %d not found", employee.getManagerId()), HttpStatus.BAD_REQUEST);
        // Пустое имя
        if (Objects.equals(employee.getFullName(), ""))
            throw new CustomApiException("Full name can't be empty", HttpStatus.BAD_REQUEST);
        return employeeRepository.insert(employee);
    }

    public List<EmployeeDto> getEmployees(Condition condition) {
        return employeeRepository.findAll(condition);
    }

    public Employee getEmployee(Integer id) throws CustomApiException {
        Employee e = employeeRepository.find(id);
        // Нет сущности с таким идентификатором
        if (e == null)
            throw new CustomApiException(String.format("Employee with id %d not found", id), HttpStatus.NOT_FOUND);
        return e;
    }

    public Boolean delete(Integer id) throws CustomApiException {
        Employee e = employeeRepository.find(id);
        // Нет сущности с таким идентификатором
        if (e == null)
            throw new CustomApiException(String.format("Employee with id %d not found", id), HttpStatus.NOT_FOUND);

        // У сотрудника есть подчинённые
        List<Employee> subordinates = employeeRepository.findSubordinatesByManagerId(id);
        if (!subordinates.isEmpty())
            throw new CustomApiException(String.format("Employee with id %d has subordinates", id), HttpStatus.BAD_REQUEST);

        // У сотрудника есть задачи
        List<Task> taskList = taskRepository.findTasksByEmployeeId(id);
        if (!taskList.isEmpty())
            throw new CustomApiException(String.format("Employee with id %d has tasks", id), HttpStatus.BAD_REQUEST);

        return employeeRepository.delete(id);
    }

    public Employee update(Integer id, Employee employee) throws CustomApiException {
        // Сотруднику назначен он же в качестве начальника
        if (Objects.equals(id, employee.getManagerId()))
            throw new CustomApiException("Employee cannot be manager of themself", HttpStatus.NOT_FOUND);
        // Начальник не существует
        if (employee.getManagerId() != null && employeeRepository.find(employee.getManagerId()) == null)
            throw new CustomApiException(String.format("Manager with id %d not found", employee.getManagerId()), HttpStatus.BAD_REQUEST);
        // Не существует должности
        if (employee.getPositionId() != null && positionRepository.find(employee.getPositionId()) == null)
            throw new CustomApiException(String.format("Position with id %d not found", employee.getPositionId()), HttpStatus.BAD_REQUEST);
        // Не существует подразделения
        if (employee.getBranchId() != null && branchRepository.find(employee.getBranchId()) == null)
            throw new CustomApiException(String.format("Branch with id %d not found", employee.getBranchId()), HttpStatus.BAD_REQUEST);
        // Пустое имя
        if (Objects.equals(employee.getFullName(), ""))
            throw new CustomApiException("Full name can't be empty", HttpStatus.BAD_REQUEST);
        //Провекра ом кого-либо не может быть его подчиненный
        checkChainOfCommand(id, employee.getManagerId());
        Employee e = employeeRepository.update(id, employee);

        // Нет сущности с таким идентификатором
        if (e == null)
            throw new CustomApiException(String.format("Employee with id %d not found", id), HttpStatus.NOT_FOUND);
        return e;
    }

    private void checkChainOfCommand(Integer employeeId, Integer managerId) throws CustomApiException {
        List<Employee> subordinates = employeeRepository.findSubordinatesByManagerId(employeeId);
        for (Employee subordinate : subordinates) {
            if (Objects.equals(subordinate.getId(), managerId))
                throw new CustomApiException(String.format("Employee with id %d can't be manager because chain of command is broken", managerId), HttpStatus.BAD_REQUEST);
            checkChainOfCommand(subordinate.getId(), managerId);
        }
    }
}
