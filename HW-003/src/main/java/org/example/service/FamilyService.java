package org.example.service;

import org.example.dto.FamilyDto;
import org.example.dto.PersonDto;
import org.example.model.Family;

import java.util.List;
import java.util.UUID;

public interface FamilyService extends ComponentService<FamilyDto, Family, PersonDto, UUID> {

	List<FamilyDto> findAllOwnerFamily(PersonDto currentPersonDto);

	boolean addMember(String email, PersonDto currentPersonDto);

	boolean exitFamily(PersonDto currentPersonDto);

}