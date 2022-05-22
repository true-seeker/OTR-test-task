package com.example.otrtesttask.Entities.Position;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionMapper {

    // List<PositionDto> -> PositionResponseDto
    public PositionResponseDto mapToPositionResponseDto(List<PositionDto> positionDtoList, Integer currentPage, Integer totalItems) {
        PositionResponseDto positionResponseDto = new PositionResponseDto();
        positionResponseDto.setPositionDtoList(positionDtoList);
        positionResponseDto.setCurrentPage(currentPage);
        positionResponseDto.setTotalItems(totalItems);
        return positionResponseDto;
    }
}
