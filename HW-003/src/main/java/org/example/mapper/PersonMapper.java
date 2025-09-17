package org.example.mapper;

import org.example.dto.PersonDto;
import org.example.model.Person;

public class PersonMapper {

	public PersonDto mapModelToDto(Person person) {
		return PersonDto.builder()
				.personId(person.getPersonId())
				.userName(person.getUserName())
				.firstName(person.getFirstName())
				.lastName(person.getLastName())
				.email(person.getEmail())
				.build();
	}

	public PersonDto mapModelToDtoLight(Person person) {
		return PersonDto.builder()
				.personId(person.getPersonId())
				.build();
	}

	public Person mapDtoToModel(PersonDto personDto) {
		return Person.builder()
				.personId(personDto.getPersonId())
				.userName(personDto.getUserName())
				.password(personDto.getPassword())
				.firstName(personDto.getFirstName())
				.lastName(personDto.getLastName())
				.email(personDto.getEmail())
				.build();
	}

	public Person mapDtoToModelLight(PersonDto personDto) {
		return Person.builder()
				.personId(personDto.getPersonId())
				.build();
	}
}
