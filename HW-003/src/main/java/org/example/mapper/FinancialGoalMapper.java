package org.example.mapper;

import org.example.dto.FinancialGoalDto;
import org.example.model.FinancialGoal;

import java.util.List;

public class FinancialGoalMapper {
	public static FinancialGoalDto modelToDto(FinancialGoal financialGoal) {
		return FinancialGoalDto.builder()
				.financialGoalId(financialGoal.getFinancialGoalId())
				.name(financialGoal.getName())
				.targetAmount(financialGoal.getTargetAmount())
				.currentAmount(financialGoal.getCurrentAmount())
				.endDate(financialGoal.getEndDate())
				.status(financialGoal.getStatus())
				.createDate(financialGoal.getCreateDate())
				.creator(financialGoal.getCreator())
				.build();
	}

	public static List<FinancialGoalDto> modelToDtoList(List<FinancialGoal> financialGoals) {
		return financialGoals.stream()
				.map(FinancialGoalMapper::modelToDto)
				.toList();
	}

	public static FinancialGoal dtoToModel(FinancialGoalDto financialGoalDto) {
		return FinancialGoal.builder()
				.financialGoalId(financialGoalDto.getFinancialGoalId())
				.name(financialGoalDto.getName())
				.targetAmount(financialGoalDto.getTargetAmount())
				.currentAmount(financialGoalDto.getCurrentAmount())
				.endDate(financialGoalDto.getEndDate())
				.status(financialGoalDto.getStatus())
				.createDate(financialGoalDto.getCreateDate())
				.creator(financialGoalDto.getCreator())
				.build();
	}
}