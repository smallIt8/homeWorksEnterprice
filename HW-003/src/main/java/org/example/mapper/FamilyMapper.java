package org.example.mapper;

import org.example.dto.FamilyDto;
import org.example.model.Family;

import java.util.List;

public class FamilyMapper {
	public static List<FamilyDto> modelToDtoList(List<Family> families) {
		return families.stream()
				.map(FamilyMapper::modelToDto)
				.toList();
	}

	public static FamilyDto modelToDto(Family family) {
		return FamilyDto.builder()
				.familyId(family.getFamilyId())
				.name(family.getName())
				.creatorDto(PersonMapper.modelToDtoLight(family.getCreator()))
				.build();
	}

	public static FamilyDto modelAndMembersToDto(Family family) {
		return FamilyDto.builder()
				.familyId(family.getFamilyId())
				.name(family.getName())
				.creatorDto(PersonMapper.modelToDtoLight(family.getCreator()))
				.membersDto(PersonMapper.modelToDtoList(family.getMembers()))
				.build();
	}

	public static List<Family> dtoToModelList(List<FamilyDto> familiesDto) {
		return familiesDto.stream()
				.map(FamilyMapper::dtoToModel)
				.toList();
	}

	public static Family dtoToModel(FamilyDto familyDto) {
		return Family.builder()
				.familyId(familyDto.getFamilyId())
				.name(familyDto.getName())
				.creator(PersonMapper.dtoToModelLight(familyDto.getCreatorDto()))
				.build();
	}

	public static Family dtoAndMembersToModel(FamilyDto familyDto) {
		return Family.builder()
				.familyId(familyDto.getFamilyId())
				.name(familyDto.getName())
				.creator(PersonMapper.dtoToModelLight(familyDto.getCreatorDto()))
				.members(PersonMapper.dtoToModelList(familyDto.getMembersDto()))
				.build();
	}

	public static Family dtoToModelLight(FamilyDto familyDto) {
		return Family.builder()
				.familyId(familyDto.getFamilyId())
				.build();
	}
}