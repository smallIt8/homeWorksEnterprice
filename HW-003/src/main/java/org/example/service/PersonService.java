package org.example.service;

import org.example.dto.PersonDto;
import org.example.model.Family;
import org.example.model.Person;

import java.util.Optional;
import java.util.UUID;

public interface PersonService extends Service<Person, PersonDto, UUID> {

	Optional<PersonDto> entry(PersonDto personDto);

	Optional<Person> getById(UUID currentPerson);

	Optional<Person> updatePassword(PersonDto currentPerson);

	void updateFamily(Person person, Family family);

}