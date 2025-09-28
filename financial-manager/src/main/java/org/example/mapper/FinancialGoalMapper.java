package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.dto.FinancialGoalDto;
import org.example.model.FinancialGoal;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class FinancialGoalMapper {

	private final PersonMapper personMapper;

	public List<FinancialGoalDto> mapModelToDtoList(List<FinancialGoal> financialGoals) {
		return financialGoals.stream()
				.map(this::mapModelToDto)
				.toList();
	}

	public FinancialGoalDto mapModelToDto(FinancialGoal financialGoal) {
		return FinancialGoalDto.builder()
				.financialGoalId(financialGoal.getFinancialGoalId())
				.financialGoalName(financialGoal.getFinancialGoalName())
				.targetAmount(financialGoal.getTargetAmount())
				.endDate(financialGoal.getEndDate())
				.creatorDto(personMapper.mapModelToDtoLight(financialGoal.getCreator()))
				.createDate(financialGoal.getCreateDate())
				.build();
	}

	public List<FinancialGoal> mapDtoToModelList(List<FinancialGoalDto> financialGoalsDto) {
		return financialGoalsDto.stream()
				.map(this::mapDtoToModel)
				.toList();
	}

	public FinancialGoal mapDtoToModel(FinancialGoalDto financialGoalDto) {
		return FinancialGoal.builder()
				.financialGoalId(financialGoalDto.getFinancialGoalId())
				.financialGoalName(financialGoalDto.getFinancialGoalName())
				.targetAmount(financialGoalDto.getTargetAmount())
				.endDate(financialGoalDto.getEndDate())
				.creator(personMapper.mapDtoToModelLight(financialGoalDto.getCreatorDto()))
				.createDate(financialGoalDto.getCreateDate())
				.build();
	}

	public FinancialGoal mapDtoToModelLight(FinancialGoalDto financialGoalDto) {
		return FinancialGoal.builder()
				.financialGoalId(financialGoalDto.getFinancialGoalId())
				.build();
	}
}
