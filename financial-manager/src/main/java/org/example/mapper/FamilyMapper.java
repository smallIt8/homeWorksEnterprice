package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.dto.FamilyDto;
import org.example.model.Family;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class FamilyMapper {

	private final PersonMapper personMapper;

	public List<FamilyDto> mapModelToDtoList(List<Family> families) {
		return families.stream()
				.map(this::mapModelToDto)
				.toList();
	}

	public FamilyDto mapModelToDto(Family family) {
		return FamilyDto.builder()
				.familyId(family.getFamilyId())
				.familyName(family.getFamilyName())
				.creatorDto(personMapper.mapModelToDtoLight(family.getCreator()))
				.build();
	}

	public List<Family> mapDtoToModelList(List<FamilyDto> familiesDto) {
		return familiesDto.stream()
				.map(this::mapDtoToModel)
				.toList();
	}

	public Family mapDtoToModel(FamilyDto familyDto) {
		return Family.builder()
				.familyId(familyDto.getFamilyId())
				.familyName(familyDto.getFamilyName())
				.creator(personMapper.mapDtoToModelLight(familyDto.getCreatorDto()))
				.build();
	}

	public Family mapDtoToModelLight(FamilyDto familyDto) {
		return Family.builder()
				.familyId(familyDto.getFamilyId())
				.build();
	}
}
