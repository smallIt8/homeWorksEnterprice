package org.example.mapper;

import org.example.dto.PersonDto;
import org.example.model.Person;

public class PersonMapper {
	public static PersonDto modelToDto(Person person) {
		return PersonDto.builder()
				.personId(person.getPersonId())
				.userName(person.getUserName())
				.firstName(person.getFirstName())
				.lastName(person.getLastName())
				.email(person.getEmail())
				.family(person.getFamily())
				.build();
	}

	public static Person dtoToModel(PersonDto personDto) {
		return Person.builder()
				.personId(personDto.getPersonId())
				.userName(personDto.getUserName())
				.password(personDto.getPassword())
				.firstName(personDto.getFirstName())
				.lastName(personDto.getLastName())
				.email(personDto.getEmail())
				.build();
	}
}