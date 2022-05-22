package com.example.otrtesttask.Entities.Branch;

import com.example.otrtesttask.jooq.Tables;
import com.example.otrtesttask.jooq.tables.pojos.Branch;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BranchRepository {
    @Autowired
    private final DSLContext dsl;

    public BranchDto insert(Branch branch) {
        return dsl.insertInto(Tables.BRANCH)
                .set(dsl.newRecord(Tables.BRANCH, branch))
                .returning()
                .fetchOneInto(BranchDto.class);
    }

    public BranchDto update(Integer id, Branch branch) {
        return dsl.update(Tables.BRANCH)
                .set(dsl.newRecord(Tables.BRANCH, branch))
                .where(Tables.BRANCH.ID.eq(id))
                .returning()
                .fetchOneInto(BranchDto.class);
    }

    public BranchDto find(Integer id) {
        return dsl.selectFrom(Tables.BRANCH)
                .where(Tables.BRANCH.ID.eq(id))
                .fetchOneInto(BranchDto.class);
    }

    public List<BranchDto> findAll(Condition condition, Integer pageSize, Integer pageNumber) {
        return dsl.selectFrom(Tables.BRANCH)
                .where(condition)
                .orderBy(Tables.BRANCH.ID)
                .limit(pageSize)
                .offset(pageNumber * pageSize)
                .fetchInto(BranchDto.class);
    }

    public Boolean delete(Integer id) {
        return dsl.deleteFrom(Tables.BRANCH)
                .where(Tables.BRANCH.ID.eq(id))
                .execute() == 1;
    }

    public Integer getTotalItems(Condition condition) {
        return dsl.selectCount()
                .from(Tables.BRANCH)
                .where(condition)
                .fetchOneInto(Integer.class);
    }
}
