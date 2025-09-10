package org.example.mapper;

import org.example.dto.FinancialGoalDto;
import org.example.model.FinancialGoal;

import java.util.List;

public class FinancialGoalMapper {
	public static List<FinancialGoalDto> modelToDtoList(List<FinancialGoal> financialGoals) {
		return financialGoals.stream()
				.map(FinancialGoalMapper::modelToDto)
				.toList();
	}

	public static FinancialGoalDto modelToDto(FinancialGoal financialGoal) {
		return FinancialGoalDto.builder()
				.financialGoalId(financialGoal.getFinancialGoalId())
				.name(financialGoal.getName())
				.targetAmount(financialGoal.getTargetAmount())
				.endDate(financialGoal.getEndDate())
				.creatorDto(PersonMapper.modelToDtoLight(financialGoal.getCreator()))
				.createDate(financialGoal.getCreateDate())
				.build();
	}

	public static List<FinancialGoal> dtoToModelList(List<FinancialGoalDto> financialGoalsDto) {
		return financialGoalsDto.stream()
				.map(FinancialGoalMapper::dtoToModel)
				.toList();
	}

	public static FinancialGoal dtoToModel(FinancialGoalDto financialGoalDto) {
		return FinancialGoal.builder()
				.financialGoalId(financialGoalDto.getFinancialGoalId())
				.name(financialGoalDto.getName())
				.targetAmount(financialGoalDto.getTargetAmount())
				.endDate(financialGoalDto.getEndDate())
				.creator(PersonMapper.dtoToModelLight(financialGoalDto.getCreatorDto()))
				.createDate(financialGoalDto.getCreateDate())
				.build();
	}

	public static FinancialGoal dtoToModelLight(FinancialGoalDto financialGoalDto) {
		return FinancialGoal.builder()
				.financialGoalId(financialGoalDto.getFinancialGoalId())
				.build();
	}
}