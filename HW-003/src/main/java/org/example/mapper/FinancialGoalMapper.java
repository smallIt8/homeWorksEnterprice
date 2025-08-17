package org.example.mapper;

import org.example.dto.FinancialGoalDto;
import org.example.model.FinancialGoal;

public class FinancialGoalMapper {
	public static FinancialGoalDto modelToDto(FinancialGoal financialGoal) {
		return new FinancialGoalDto(
				financialGoal.getFinancialGoalId(),
				financialGoal.getFinancialGoalName(),
				financialGoal.getTargetAmount(),
				financialGoal.getCurrentAmount(),
				financialGoal.getEndDate(),
				financialGoal.getStatus(),
				financialGoal.getCreateDate(),
				financialGoal.getPerson());
	}

	public static FinancialGoal dtoToModel(FinancialGoalDto financialGoalDto) {
		return new FinancialGoal(
				financialGoalDto.getFinancialGoalId(),
				financialGoalDto.getFinancialGoalName(),
				financialGoalDto.getTargetAmount(),
				financialGoalDto.getCurrentAmount(),
				financialGoalDto.getEndDate(),
				financialGoalDto.getStatus(),
				financialGoalDto.getCreateDate(),
				financialGoalDto.getPerson());
	}
}