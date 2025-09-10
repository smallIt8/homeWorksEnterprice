package org.example.mapper;

import org.example.dto.BudgetDto;
import org.example.model.Budget;

import java.util.List;

public class BudgetMapper {
	public static List<BudgetDto> modelToDtoList(List<Budget> budgets) {
		return budgets.stream()
				.map(BudgetMapper::modelToDto)
				.toList();
	}

	public static BudgetDto modelToDto(Budget budget) {
		return BudgetDto.builder()
				.budgetId(budget.getBudgetId())
				.name(budget.getName())
				.categoryDto(CategoryMapper.modelToDtoLight(budget.getCategory()))
				.limit(budget.getLimit())
				.period(budget.getPeriod())
				.creatorDto(PersonMapper.modelToDtoLight(budget.getCreator()))
				.build();
	}

	public static List<Budget> dtoToModelList(List<BudgetDto> budgetsDto) {
		return budgetsDto.stream()
				.map(BudgetMapper::dtoToModel)
				.toList();
	}

	public static Budget dtoToModel(BudgetDto budgetDto) {
		return Budget.builder()
				.budgetId(budgetDto.getBudgetId())
				.name(budgetDto.getName())
				.category(CategoryMapper.dtoToModelLight(budgetDto.getCategoryDto()))
				.limit(budgetDto.getLimit())
				.period(budgetDto.getPeriod())
				.creator(PersonMapper.dtoToModelLight(budgetDto.getCreatorDto()))
				.build();
	}

	public static Budget dtoToModelLight(BudgetDto budgetDto) {
		return Budget.builder()
				.budgetId(budgetDto.getBudgetId())
				.build();
	}
}