package com.example.otrtesttask.service;

import com.example.otrtesttask.exceptions.CustomApiException;
import com.example.otrtesttask.jooq.tables.pojos.Branch;
import com.example.otrtesttask.repository.BranchRepository;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {
    @Autowired
    private BranchRepository branchRepository;

    public Branch create(Branch branch) throws CustomApiException {
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

    public Boolean delete(Integer id) {
        return branchRepository.delete(id);
    }
}
