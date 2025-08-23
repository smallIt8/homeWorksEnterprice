package org.example.service;

import org.example.dto.FamilyDto;
import org.example.dto.PersonDto;
import org.example.model.Person;

import java.util.Optional;
import java.util.UUID;

public interface PersonService {

	Optional<PersonDto> entry(PersonDto personDto);

	void create(PersonDto personDto);

	Optional<PersonDto> update(PersonDto currentPersonDto);

	Optional<Person> findById(UUID currentPerson);

	Optional<PersonDto> updatePassword(PersonDto currentPersonDto);

	void updateFamily(PersonDto personOwnerDto, FamilyDto familyDto);

	void delete(PersonDto currentPersonDto);

}