package com.example.otrtesttask.dto;

import lombok.Data;

import java.util.List;

@Data
public class BranchResponseDto {
    private Integer totalItems;
    private Integer currentPage;
    private List<BranchDto> branchDtoList;
}
