package org.example.mapper;

import org.example.dto.BudgetDto;
import org.example.model.Budget;

public class BudgetMapper {
	public static BudgetDto modelToDto(Budget budget) {
		return new BudgetDto(
				budget.getBudgetId(),
				budget.getBudgetName(),
				budget.getCategory(),
				budget.getLimit(),
				budget.getPeriod(),
				budget.getPerson());
	}

	public static Budget dtoToModel(BudgetDto budgetDto) {
		return new Budget(
				budgetDto.getBudgetId(),
				budgetDto.getBudgetName(),
				budgetDto.getCategory(),
				budgetDto.getLimit(),
				budgetDto.getPeriod(),
				budgetDto.getPerson());
	}
}