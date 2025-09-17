package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.dto.BudgetDto;
import org.example.model.Budget;

import java.util.List;

@RequiredArgsConstructor
public class BudgetMapper {

	private final CategoryMapper categoryMapper;
	private final PersonMapper personMapper;

	public List<BudgetDto> mapModelToDtoList(List<Budget> budgets) {
		return budgets.stream()
				.map(this::mapModelToDto)
				.toList();
	}

	public BudgetDto mapModelToDto(Budget budget) {
		return BudgetDto.builder()
				.budgetId(budget.getBudgetId())
				.budgetName(budget.getBudgetName())
				.categoryDto(categoryMapper.mapModelToDtoLight(budget.getCategory()))
				.limit(budget.getLimit())
				.period(budget.getPeriod())
				.creatorDto(personMapper.mapModelToDtoLight(budget.getCreator()))
				.build();
	}

	public List<Budget> mapDtoToModelList(List<BudgetDto> budgetsDto) {
		return budgetsDto.stream()
				.map(this::mapDtoToModel)
				.toList();
	}

	public Budget mapDtoToModel(BudgetDto budgetDto) {
		return Budget.builder()
				.budgetId(budgetDto.getBudgetId())
				.budgetName(budgetDto.getBudgetName())
				.category(categoryMapper.mapDtoToModelLight(budgetDto.getCategoryDto()))
				.limit(budgetDto.getLimit())
				.period(budgetDto.getPeriod())
				.creator(personMapper.mapDtoToModelLight(budgetDto.getCreatorDto()))
				.build();
	}

	public Budget mapDtoToModelLight(BudgetDto budgetDto) {
		return Budget.builder()
				.budgetId(budgetDto.getBudgetId())
				.build();
	}
}
