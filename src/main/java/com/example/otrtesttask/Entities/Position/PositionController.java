package com.example.otrtesttask.Entities.Position;

import com.example.otrtesttask.Exceptions.CustomApiException;
import com.example.otrtesttask.jooq.tables.pojos.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/positions")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @PostMapping("/")
    public ResponseEntity<PositionDto> createPosition(@RequestBody Position position) throws CustomApiException {
//        Добавление должности
        PositionDto p = positionService.create(position);
        return ResponseEntity.ok(p);
    }

    @GetMapping("/")
    public ResponseEntity<PositionResponseDto> getPositions(@RequestParam(required = false) String title,
                                                            @RequestParam(required = false, defaultValue = "50") Integer pageSize,
                                                            @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {

//        Получение списка должностей
//        Возможен фильтр по полю title
        PositionDto positionDto = new PositionDto();
        positionDto.setTitle(title);

        PositionResponseDto positionResponseDto = positionService.getPositions(positionDto, pageSize, pageNumber);
        return ResponseEntity.ok(positionResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionDto> getPosition(@PathVariable(value = "id") Integer id) throws CustomApiException {
//        Получение должности по идентификатору
        PositionDto p = positionService.getPosition(id);
        return ResponseEntity.ok(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PositionDto> updatePosition(@PathVariable(value = "id") Integer id,
                                                   @RequestBody Position position) throws CustomApiException {
//        Изменение информации о должности по идентификатору
        PositionDto p = positionService.update(id, position);
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
