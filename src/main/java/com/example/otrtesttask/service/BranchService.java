package com.example.otrtesttask.service;

import com.example.otrtesttask.exceptions.CustomApiException;
import com.example.otrtesttask.jooq.tables.pojos.Branch;
import com.example.otrtesttask.jooq.tables.pojos.Employee;
import com.example.otrtesttask.repository.BranchRepository;
import com.example.otrtesttask.repository.EmployeeRepository;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public Branch create(Branch branch) throws CustomApiException {

        // Если передали id, то возвращаем ошибку
        if (branch.getId() != null)
            throw new CustomApiException("Id field is prohibited", HttpStatus.BAD_REQUEST);
        // В запросе не передан title
        if (branch.getTitle() == null)
            throw new CustomApiException("Missing required field: title", HttpStatus.BAD_REQUEST);

        return branchRepository.insert(branch);
    }

    public Branch update(Integer id, Branch branch) throws CustomApiException {
        Branch b = branchRepository.update(id, branch);
        if (b == null)
            throw new CustomApiException(String.format("Branch with id %d not found", id), HttpStatus.NOT_FOUND);
        return b;
    }

    public Branch getBranch(Integer id) throws CustomApiException {
        Branch b = branchRepository.find(id);
        if (b == null)
            throw new CustomApiException(String.format("Branch with id %d not found", id), HttpStatus.NOT_FOUND);
        return b;
    }

    public List<Branch> getBranches(Condition condition) {
        return branchRepository.findAll(condition);
    }

    public Boolean delete(Integer id) throws CustomApiException {

        List<Employee> employees = employeeRepository.findEmployeesByBranchId(id);
        if (employees != null)
            throw new CustomApiException(String.format("Some employees are attached to branch with %d", id), HttpStatus.BAD_REQUEST);

        Boolean b = branchRepository.delete(id);
        if (!b)
            throw new CustomApiException(String.format("Branch with id %d not found", id), HttpStatus.NOT_FOUND);
        return b;
    }
}
