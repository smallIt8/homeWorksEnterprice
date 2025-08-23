package org.example.mapper;

import org.example.dto.FamilyDto;
import org.example.model.Family;

import java.util.List;

public class FamilyMapper {
	public static FamilyDto modelToDto(Family family) {
		return new FamilyDto(
				family.getFamilyId(),
				family.getName(),
				family.getCreator(),
				family.getMembers());
	}

	public static List<FamilyDto> modelToDtoList(List<Family> families) {
		return families.stream()
				.map(FamilyMapper::modelToDto)
				.toList();
	}

	public static Family dtoToModel(FamilyDto familyDto) {
		return new Family(
				familyDto.getFamilyId(),
				familyDto.getName(),
				familyDto.getCreator(),
				familyDto.getMembers());
	}
}