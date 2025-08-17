package org.example.service;

import org.example.dto.PersonDto;
import org.example.model.Family;
import org.example.model.Person;

import java.util.Optional;
import java.util.UUID;

public interface PersonService extends Service<PersonDto, PersonDto, UUID> {

	Optional<PersonDto> entry(PersonDto personDto);

	Optional<Person> getById(UUID currentPerson);

	Optional<PersonDto> updatePassword(PersonDto currentPersonDto);

	void updateFamily(Person person, Family family);

}