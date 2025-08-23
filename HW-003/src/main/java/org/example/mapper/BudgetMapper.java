package org.example.mapper;

import org.example.dto.BudgetDto;
import org.example.model.Budget;

import java.util.List;

public class BudgetMapper {
	public static BudgetDto modelToDto(Budget budget) {
		return BudgetDto.builder()
				.budgetId(budget.getBudgetId())
				.name(budget.getName())
				.category(budget.getCategory())
				.limit(budget.getLimit())
				.period(budget.getPeriod())
				.creator(budget.getCreator())
				.build();
	}

	public static List<BudgetDto> modelToDtoList(List<Budget> budgets) {
		return budgets.stream()
				.map(BudgetMapper::modelToDto)
				.toList();
	}

	public static Budget dtoToModel(BudgetDto budgetDto) {
		return Budget.builder()
				.budgetId(budgetDto.getBudgetId())
				.name(budgetDto.getName())
				.category(budgetDto.getCategory())
				.limit(budgetDto.getLimit())
				.period(budgetDto.getPeriod())
				.creator(budgetDto.getCreator())
				.build();
	}
}