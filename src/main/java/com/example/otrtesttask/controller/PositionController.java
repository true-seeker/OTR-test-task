package com.example.otrtesttask.controller;

import com.example.otrtesttask.dto.PositionDto;
import com.example.otrtesttask.exceptions.CustomApiException;
import com.example.otrtesttask.jooq.tables.pojos.Position;
import com.example.otrtesttask.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positions")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @PostMapping("/")
    public ResponseEntity<Position> createPosition(@RequestBody Position position) throws CustomApiException {
//        Добавление должности
        Position p = positionService.create(position);
        return ResponseEntity.ok(p);
    }

    @GetMapping("/")
    public ResponseEntity<List<Position>> getPositions(@RequestParam(required = false) String title) {
//        Получение списка должностей
//        Возможен фильтр по полю title
        PositionDto positionDto = new PositionDto();
        positionDto.setTitle(title);

        List<Position> positionList = positionService.getPositions(positionDto);
        return ResponseEntity.ok(positionList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> getPosition(@PathVariable(value = "id") Integer id) throws CustomApiException {
//        Получение должности по идентификатору
        Position p = positionService.getPosition(id);
        return ResponseEntity.ok(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Position> updatePosition(@PathVariable(value = "id") Integer id,
                                                   @RequestBody Position position) throws CustomApiException {
//        Изменение информации о должности по идентификатору
        Position p = positionService.update(id, position);
        return ResponseEntity.ok(p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePosition(@PathVariable(value = "id") Integer id) throws CustomApiException {
//        Удаление должности
        Boolean b = positionService.delete(id);
        if (b)
            return ResponseEntity.ok("Ok");
        else
            return ResponseEntity.badRequest().body("Error");
    }
}
