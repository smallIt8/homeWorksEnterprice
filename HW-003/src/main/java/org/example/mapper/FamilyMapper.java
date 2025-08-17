package org.example.mapper;

import org.example.dto.FamilyDto;
import org.example.model.Family;

public class FamilyMapper {
	public static FamilyDto modelToDto(Family family) {
		return new FamilyDto(
				family.getFamilyId(),
				family.getFamilyName(),
				family.getOwnerPersonId(),
				family.getMembers());
	}

	public static Family dtoToModel(FamilyDto familyDto) {
		return new Family(
				familyDto.getFamilyId(),
				familyDto.getFamilyName(),
				familyDto.getOwnerPersonId(),
				familyDto.getMembers());
	}
}