package com.example.otrtesttask.Entities.Position;

import com.example.otrtesttask.jooq.Tables;
import com.example.otrtesttask.jooq.tables.pojos.Position;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PositionRepository {

    @Autowired
    private final DSLContext dsl;

    public PositionDto insert(Position position) {
        return dsl.insertInto(Tables.POSITION)
                .set(dsl.newRecord(Tables.POSITION, position))
                .returning()
                .fetchOneInto(PositionDto.class);
    }

    public PositionDto update(Integer id, Position position) {
        return dsl.update(Tables.POSITION)
                .set(dsl.newRecord(Tables.POSITION, position))
                .where(Tables.POSITION.ID.eq(id))
                .returning()
                .fetchOneInto(PositionDto.class);
    }

    public PositionDto find(Integer id) {
        return dsl.selectFrom(Tables.POSITION)
                .where(Tables.POSITION.ID.eq(id))
                .fetchOneInto(PositionDto.class);
    }

    public List<PositionDto> findAll(Condition condition, Integer pageSize, Integer pageNumber) {
        return dsl.selectFrom(Tables.POSITION)
                .where(condition)
                .orderBy(Tables.POSITION.ID)
                .limit(pageSize)
                .offset(pageNumber * pageSize)
                .fetchInto(PositionDto.class);
    }

    public Boolean delete(Integer id) {
        return dsl.deleteFrom(Tables.POSITION)
                .where(Tables.POSITION.ID.eq(id))
                .execute() == 1;
    }

    public Integer getTotalItems(Condition condition) {
        return dsl.selectCount()
                .from(Tables.POSITION)
                .where(condition)
                .fetchOneInto(Integer.class);
    }
}
