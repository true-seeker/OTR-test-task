package com.example.otrtesttask.Entities.Branch;

import com.example.otrtesttask.Entities.Employee.EmployeeDto;
import com.example.otrtesttask.Entities.Employee.EmployeeRepository;
import com.example.otrtesttask.Exceptions.CustomApiException;
import com.example.otrtesttask.jooq.Tables;
import com.example.otrtesttask.jooq.tables.pojos.Branch;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jooq.impl.DSL.trueCondition;

@Service
public class BranchService {
    private final BranchMapper branchMapper = new BranchMapper();
    private final Integer defaultPageSize = 50;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public BranchDto create(Branch branch) throws CustomApiException {

        // Если передали id, то возвращаем ошибку
        if (branch.getId() != null)
            throw new CustomApiException("Id field is prohibited", HttpStatus.BAD_REQUEST);
        // В запросе не передан title
        if (branch.getTitle() == null)
            throw new CustomApiException("Missing required field: title", HttpStatus.BAD_REQUEST);

        return branchRepository.insert(branch);
    }

    public BranchDto update(Integer id, Branch branch) throws CustomApiException {
        BranchDto b = branchRepository.update(id, branch);
        // Нет сущности с таким идентификатором
        if (b == null)
            throw new CustomApiException(String.format("Branch with id %d not found", id), HttpStatus.NOT_FOUND);


        return b;
    }

    public BranchDto getBranch(Integer id) throws CustomApiException {
        BranchDto branchDto = branchRepository.find(id);
        // Нет сущности с таким идентификатором
        if (branchDto == null)
            throw new CustomApiException(String.format("Branch with id %d not found", id), HttpStatus.NOT_FOUND);

        return branchDto;
    }

    public BranchResponseDto getBranches(BranchDto branchDto, Integer pageSize, Integer pageNumber) {
        if (pageSize > defaultPageSize)
            pageSize = defaultPageSize;

        Condition condition = trueCondition();
        if (branchDto.getTitle() != null) {
            condition = condition.and(Tables.BRANCH.TITLE.containsIgnoreCase(branchDto.getTitle()));
        }

        return branchMapper.mapToBranchResponseDto(
                branchRepository.findAll(condition, pageSize, pageNumber),
                pageNumber,
                branchRepository.getTotalItems(condition));
    }

    public Boolean delete(Integer id) throws CustomApiException {

        List<EmployeeDto> employees = employeeRepository.findEmployeesByBranchId(id);
        // Это подразделение назначено какому-то сотруднику
        if (!employees.isEmpty())
            throw new CustomApiException(String.format("Some employees are attached to branch with id %d", id), HttpStatus.BAD_REQUEST);

        Boolean b = branchRepository.delete(id);
        // Нет сущности с таким идентификатором
        if (!b)
            throw new CustomApiException(String.format("Branch with id %d not found", id), HttpStatus.NOT_FOUND);
        return b;
    }
}
