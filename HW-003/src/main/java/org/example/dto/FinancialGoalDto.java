package org.example.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.RegexConstant.FINANCIAL_GOAL_NAME_REGEX;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialGoalDto {

	private UUID financialGoalId;

	@NotBlank(message = WARNING_FINANCIAL_GOAL_NAME_NOT_NULL_MESSAGE)
	@Pattern(regexp = FINANCIAL_GOAL_NAME_REGEX,
			message = WARNING_ENTER_FINANCIAL_GOAL_NAME_MESSAGE
	)
	private String financialGoalName;

	@NotNull(message = WARNING_FINANCIAL_GOAL_TARGET_AMOUNT_NOT_NULL_MESSAGE)
	@DecimalMin(value = "0.01", message = WARNING_ENTER_FINANCIAL_GOAL_TARGET_AMOUNT_MESSAGE)
	private BigDecimal targetAmount;

	@NotNull(message = WARNING_FINANCIAL_GOAL_END_DATE_NOT_NULL_MESSAGE)
	@FutureOrPresent(message = WARNING_FINANCIAL_GOAL_END_DATE_NOT_CURRENT_OR_FUTURE_MESSAGE)
	private LocalDate endDate;
	private PersonDto creatorDto;
	private LocalDateTime createDate;
}
