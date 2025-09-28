package org.example.validator;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.BudgetDto;
import org.example.dto.CategoryDto;
import org.example.model.CategoryType;

import static org.example.util.constant.ErrorMessageConstant.*;

@Slf4j
public class BudgetValidator implements Validator<BudgetDto> {

	@Override
	public void validate(BudgetDto budgetDto) {
		validateCategoryType(budgetDto.getCategoryDto());
	}

	private void validateCategoryType(CategoryDto categoryDto) {
		if (CategoryType.EXPENSE.equals(categoryDto.getType())) {
			log.error("Неверный тип категории для бюджета: '{}'", categoryDto.getCategoryName());
			throw new IllegalArgumentException(WARNING_SELECT_BUDGET_CATEGORY_NOT_INCOME_MESSAGE);
		}
		log.info("Категория бюджета успешно проверена: '{}'", categoryDto.getCategoryName());
	}
}
