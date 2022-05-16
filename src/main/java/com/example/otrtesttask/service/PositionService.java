package com.example.otrtesttask.service;

import com.example.otrtesttask.jooq.tables.pojos.Position;
import com.example.otrtesttask.repository.PositionRepository;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {
    @Autowired
    private PositionRepository positionRepository;

    public Position create(Position position) {
        return positionRepository.insert(position);
    }

    public List<Position> getPositions(Condition condition) {
        return positionRepository.findAll(condition);
    }

    public Position getPosition(Integer id) {
        return positionRepository.find(id);
    }

    public Boolean delete(Integer id) {
        return positionRepository.delete(id);
    }

    public Position update(Integer id, Position position) {
        return positionRepository.update(id, position);
    }
}
