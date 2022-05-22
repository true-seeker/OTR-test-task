package com.example.otrtesttask.Entities.Branch;

import com.example.otrtesttask.jooq.tables.pojos.Branch;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchMapper {

    // Branch -> BranchDto
    public BranchDto mapToBranchDto(Branch branch) {
        BranchDto branchDto = new BranchDto();

        branchDto.setId(branch.getId());
        branchDto.setTitle(branch.getTitle());
        return branchDto;
    }

    // List<BranchDto> -> BranchResponseDto
    public BranchResponseDto mapToBranchResponseDto(List<BranchDto> branchDtoList, Integer currentPage, Integer totalItems) {
        BranchResponseDto branchResponseDto = new BranchResponseDto();
        branchResponseDto.setBranchDtoList(branchDtoList);
        branchResponseDto.setCurrentPage(currentPage);
        branchResponseDto.setTotalItems(totalItems);
        return branchResponseDto;
    }
}
