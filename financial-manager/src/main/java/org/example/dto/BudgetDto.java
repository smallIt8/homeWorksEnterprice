package org.example.dto;

import jakarta.persistence.Convert;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.util.DateConverterUtil;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.RegexConstant.BUDGET_NAME_REGEX;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDto {

	private UUID budgetId;

	@NotBlank(message = WARNING_BUDGET_NAME_NOT_NULL_MESSAGE)
	@Pattern(
			regexp = BUDGET_NAME_REGEX,
			message = WARNING_ENTER_BUDGET_NAME_MESSAGE
	)
	private String budgetName;

	@NotNull(message = WARNING_SELECT_BUDGET_CATEGORY_NOT_NULL_MESSAGE)
	private CategoryDto categoryDto;

	@NotNull(message = WARNING_BUDGET_LIMIT_NOT_NULL_MESSAGE)
	@DecimalMin(
			value = "0.01",
			message = WARNING_ENTER_BUDGET_LIMIT_MESSAGE
	)
	private BigDecimal limit;

	@NotNull(message = WARNING_BUDGET_PERIOD_NOT_NULL_MESSAGE)
	@FutureOrPresent(message = WARNING_BUDGET_PERIOD_NOT_CURRENT_OR_FUTURE_MESSAGE)
	@Convert(converter = DateConverterUtil.class)
	private YearMonth period;

	private PersonDto creatorDto;
}
