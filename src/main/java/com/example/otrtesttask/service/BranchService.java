package com.example.otrtesttask.service;

import com.example.otrtesttask.jooq.tables.pojos.Branch;
import com.example.otrtesttask.repository.BranchRepository;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {
    @Autowired
    private BranchRepository branchRepository;

    public Branch create(Branch branch) {
        return branchRepository.insert(branch);
    }

    public Branch update(Integer id, Branch branch) {
        return branchRepository.update(id, branch);
    }

    public Branch getBranch(Integer id) {
        return branchRepository.find(id);
    }

    public List<Branch> getBranches(Condition condition) {
        return branchRepository.findAll(condition);
    }

    public Boolean delete(Integer id) {
        return branchRepository.delete(id);
    }
}
