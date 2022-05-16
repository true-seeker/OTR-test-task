package com.example.otrtesttask.repository;

import com.example.otrtesttask.jooq.tables.pojos.Position;
import com.example.otrtesttask.jooq.Tables;

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

    public Position insert(Position position) {
        return dsl.insertInto(Tables.POSITION)
                .set(dsl.newRecord(Tables.POSITION, position))
                .returning()
                .fetchOneInto(Position.class);
    }

    public Position update(Integer id, Position position) {
        return dsl.update(Tables.POSITION)
                .set(dsl.newRecord(Tables.POSITION, position))
                .where(Tables.POSITION.ID.eq(id))
                .returning()
                .fetchOneInto(Position.class);
    }

    public Position find(Integer id) {
        return dsl.selectFrom(Tables.POSITION)
                .where(Tables.POSITION.ID.eq(id))
                .fetchOneInto(Position.class);
    }

    public List<Position> findAll(Condition condition) {
        return dsl.selectFrom(Tables.POSITION)
                .where(condition)
                .orderBy(Tables.POSITION.ID)
                .fetchInto(Position.class);
    }

    public Boolean delete(Integer id) {
        return dsl.deleteFrom(Tables.POSITION)
                .where(Tables.POSITION.ID.eq(id))
                .execute() == 1;
    }

}
