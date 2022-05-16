package com.example.otrtesttask.controller;

import com.example.otrtesttask.jooq.Tables;
import com.example.otrtesttask.jooq.tables.pojos.Branch;
import com.example.otrtesttask.service.BranchService;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.jooq.impl.DSL.trueCondition;

@RestController
@RequestMapping("/branches")
public class BranchController {
    @Autowired
    BranchService branchService;

    @PostMapping("/")
    public ResponseEntity<Object> createBranch(@RequestBody Branch branch) {
        Branch b = branchService.create(branch);
        return ResponseEntity.ok(b);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getBranches(@RequestParam(required = false) String title) {
        Condition condition = trueCondition();

        if (title != null) {
            condition = condition.and(Tables.BRANCH.TITLE.containsIgnoreCase(title));
        }

        List<Branch> branchList = branchService.getBranches(condition);
        return ResponseEntity.ok(branchList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBranch(@PathVariable(value = "id") Integer id) {
        Branch b = branchService.getBranch(id);
        return ResponseEntity.ok(b);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBranch(@PathVariable(value = "id") Integer id,
                                               @RequestBody Branch branch) {
        Branch b = branchService.update(id, branch);
        return ResponseEntity.ok(b);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBranch(@PathVariable(value = "id") Integer id) {
        Boolean b = branchService.delete(id);
        if (b)
            return ResponseEntity.ok("Ok");
        else
            return ResponseEntity.badRequest().body("Error");
    }
}
