package com.example.otrtesttask.Entities.Branch;

import lombok.Data;

import java.util.List;

@Data
public class BranchResponseDto {
    private Integer totalItems;
    private Integer currentPage;
    private List<BranchDto> branchDtoList;
}
