package com.example.otrtesttask.service;

import com.example.otrtesttask.exceptions.CustomApiException;
import com.example.otrtesttask.jooq.tables.pojos.Position;
import com.example.otrtesttask.repository.PositionRepository;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {
    @Autowired
    private PositionRepository positionRepository;

    public Position create(Position position) throws CustomApiException {
        if (position.getId() != null)
            throw new CustomApiException("Id field is prohibited", HttpStatus.BAD_REQUEST);
        if (position.getTitle() == null)
            throw new CustomApiException("Missing required field: title", HttpStatus.BAD_REQUEST);
        return positionRepository.insert(position);
    }

    public List<Position> getPositions(Condition condition) {
        return positionRepository.findAll(condition);
    }

    public Position getPosition(Integer id) throws CustomApiException {
        Position p = positionRepository.find(id);
        if (p == null)
            throw new CustomApiException(String.format("Position with id %d not found", id), HttpStatus.NOT_FOUND);
        return p;
    }

    public Boolean delete(Integer id) throws CustomApiException {
        Boolean b = positionRepository.delete(id);
        if (!b)
            throw new CustomApiException(String.format("Branch with id %d not found", id), HttpStatus.NOT_FOUND);
        return b;
    }

    public Position update(Integer id, Position position) throws CustomApiException {
        Position p = positionRepository.update(id, position);
        if (p == null)
            throw new CustomApiException(String.format("Position with id %d not found", id), HttpStatus.NOT_FOUND);
        return p;
    }
}
