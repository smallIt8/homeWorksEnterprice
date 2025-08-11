package org.example.mapper;

import org.example.dto.PersonDto;
import org.example.model.Person;

public class PersonMapper {
	public static PersonDto modelToDto(Person person) {
		return new PersonDto(
				person.getPersonId(),
				person.getUserName(),
				person.getFirstName(),
				person.getLastName(),
				person.getEmail(),
				person.getFamily());
	}

	public static Person dtoToModel(PersonDto personDto) {
		return new Person(
				personDto.getPersonId(),
				personDto.getUserName(),
				personDto.getPassword(),
				personDto.getFirstName(),
				personDto.getLastName(),
				personDto.getEmail());
	}
}