package com.example.otrtesttask.controller;

import com.example.otrtesttask.jooq.Tables;
import com.example.otrtesttask.jooq.tables.pojos.Position;
import com.example.otrtesttask.service.PositionService;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.jooq.impl.DSL.trueCondition;

@RestController
@RequestMapping("/positions")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @PostMapping("/")
    public ResponseEntity<Object> createPosition(@RequestBody Position position) {
        Position p = positionService.create(position);
        return ResponseEntity.ok(p);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getPositions(@RequestParam(required = false) String title) {
        Condition condition = trueCondition();
        if (title != null)
            condition = condition.and(Tables.POSITION.TITLE.contains(title));

        List<Position> positionList = positionService.getPositions(condition);
        return ResponseEntity.ok(positionList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPosition(@PathVariable(value = "id") Integer id) {
        Position p = positionService.getPosition(id);
        return ResponseEntity.ok(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePosition(@PathVariable(value = "id") Integer id,
                                                 @RequestBody Position position) {
        Position p = positionService.update(id, position);
        return ResponseEntity.ok(p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePosition(@PathVariable(value = "id") Integer id) {
        Boolean b = positionService.delete(id);
        if (b)
            return ResponseEntity.ok("Ok");
        else
            return ResponseEntity.badRequest().body("Error");
    }
}
