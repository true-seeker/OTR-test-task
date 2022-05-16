package com.example.otrtesttask.repository;

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

    public Branch insert(Branch branch) {
        return dsl.insertInto(Tables.BRANCH)
                .set(dsl.newRecord(Tables.BRANCH, branch))
                .returning()
                .fetchOneInto(Branch.class);
    }

    public Branch update(Integer id, Branch branch) {
        return dsl.update(Tables.BRANCH)
                .set(dsl.newRecord(Tables.BRANCH, branch))
                .where(Tables.BRANCH.ID.eq(id))
                .returning()
                .fetchOneInto(Branch.class);
    }

    public Branch find(Integer id) {
        return dsl.selectFrom(Tables.BRANCH)
                .where(Tables.BRANCH.ID.eq(id))
                .fetchOneInto(Branch.class);
    }

    public List<Branch> findAll(Condition condition) {
        return dsl.selectFrom(Tables.BRANCH)
                .where(condition)
                .fetchInto(Branch.class);
    }

    public Boolean delete(Integer id) {
        return dsl.deleteFrom(Tables.BRANCH)
                .where(Tables.BRANCH.ID.eq(id))
                .execute() == 1;
    }
}
