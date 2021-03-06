package com.example.otrtesttask.Entities.Position;

import com.example.otrtesttask.Entities.Employee.EmployeeDto;
import com.example.otrtesttask.Entities.Employee.EmployeeRepository;
import com.example.otrtesttask.Exceptions.CustomApiException;
import com.example.otrtesttask.jooq.Tables;
import com.example.otrtesttask.jooq.tables.pojos.Position;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jooq.impl.DSL.trueCondition;

@Service
public class PositionService {
    private final PositionMapper positionMapper = new PositionMapper();
    private final Integer defaultPageSize = 50;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public PositionDto create(Position position) throws CustomApiException {
        // Если передали id, то возвращаем ошибку
        if (position.getId() != null)
            throw new CustomApiException("Id field is prohibited", HttpStatus.BAD_REQUEST);
        // В запросе не передан title
        if (position.getTitle() == null)
            throw new CustomApiException("Missing required field: title", HttpStatus.BAD_REQUEST);

        return positionRepository.insert(position);
    }

    public PositionResponseDto getPositions(PositionDto positionDto, Integer pageSize, Integer pageNumber) {
        if (pageSize > defaultPageSize)
            pageSize = defaultPageSize;

        Condition condition = trueCondition();
        if (positionDto.getTitle() != null)
            condition = condition.and(Tables.POSITION.TITLE.containsIgnoreCase(positionDto.getTitle()));

        return positionMapper.mapToPositionResponseDto(positionRepository.findAll(condition, pageSize, pageNumber),
                pageNumber,
                positionRepository.getTotalItems(condition));
    }

    public PositionDto getPosition(Integer id) throws CustomApiException {
        PositionDto p = positionRepository.find(id);
        // Нет сущности с таким идентификатором
        if (p == null)
            throw new CustomApiException(String.format("Position with id %d not found", id), HttpStatus.NOT_FOUND);
        return p;
    }

    public Boolean delete(Integer id) throws CustomApiException {

        List<EmployeeDto> employees = employeeRepository.findEmployeesByPositionId(id);
        // Эта должность назначена какому-то сотруднику
        if (!employees.isEmpty())
            throw new CustomApiException(String.format("Some employees are attached to position with id %d", id), HttpStatus.BAD_REQUEST);

        Boolean b = positionRepository.delete(id);
        if (!b)
            throw new CustomApiException(String.format("Branch with id %d not found", id), HttpStatus.NOT_FOUND);
        return b;
    }

    public PositionDto update(Integer id, Position position) throws CustomApiException {
        PositionDto p = positionRepository.update(id, position);
        // Нет сущности с таким идентификатором
        if (p == null)
            throw new CustomApiException(String.format("Position with id %d not found", id), HttpStatus.NOT_FOUND);
        return p;
    }
}
