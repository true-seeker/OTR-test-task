package com.example.otrtesttask.Entities.Position;

import lombok.Data;

import java.util.List;
@Data
public class PositionResponseDto {
    private Integer totalItems;
    private Integer currentPage;
    private List<PositionDto> positionDtoList;
}
