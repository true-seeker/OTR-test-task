package com.example.otrtesttask.controller;

import com.example.otrtesttask.dto.BranchDto;
import com.example.otrtesttask.dto.BranchResponseDto;
import com.example.otrtesttask.exceptions.CustomApiException;
import com.example.otrtesttask.jooq.tables.pojos.Branch;
import com.example.otrtesttask.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branches")
public class BranchController {
    @Autowired
    BranchService branchService;


    @PostMapping("/")
    public ResponseEntity<BranchDto> createBranch(@RequestBody Branch branch) throws CustomApiException {
//        Добавление подразделения
        BranchDto b = branchService.create(branch);
        return ResponseEntity.ok(b);
    }

    @GetMapping("/")
    public ResponseEntity<BranchResponseDto> getBranches(@RequestParam(required = false) String title,
                                                         @RequestParam(required = false, defaultValue = "50") Integer pageSize,
                                                         @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {
//        Получение списка подразделений
//        Возможен фильтр по полю title
        BranchDto branchDto = new BranchDto();
        branchDto.setTitle(title);

        BranchResponseDto branchResponseDto = branchService.getBranches(branchDto, pageSize, pageNumber);
        return ResponseEntity.ok(branchResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> getBranch(@PathVariable(value = "id") Integer id) throws CustomApiException {
//        Получение подразделения по идентификатору
        BranchDto b = branchService.getBranch(id);
        return ResponseEntity.ok(b);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDto> updateBranch(@PathVariable(value = "id") Integer id,
                                                  @RequestBody Branch branch) throws CustomApiException {
//        Обновление информации о подразделении
        BranchDto b = branchService.update(id, branch);
        return ResponseEntity.ok(b);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBranch(@PathVariable(value = "id") Integer id) throws CustomApiException {
//        Удаление подразделения по идентификатору
        Boolean b = branchService.delete(id);
        if (b)
            return ResponseEntity.ok("Branch was deleted successfully");
        else
            return ResponseEntity.badRequest().body("Error");
    }
}
