package org.example.mapper;

import org.example.dto.PersonDto;
import org.example.model.Person;

import java.util.List;

public class PersonMapper {
	public static List<PersonDto> modelToDtoList(List<Person> persons) {
		return persons.stream()
				.map(PersonMapper::modelToDto)
				.toList();
	}

	public static PersonDto modelToDto(Person person) {
		return PersonDto.builder()
				.personId(person.getPersonId())
				.userName(person.getUserName())
				.firstName(person.getFirstName())
				.lastName(person.getLastName())
				.email(person.getEmail())
				//.familyDto(FamilyMapper.modelToDto(person.getFamily()))
				.build();
	}

	public static PersonDto modelToDtoLight(Person person) {
		return PersonDto.builder()
				.personId(person.getPersonId())
				.build();
	}

	public static List<Person> dtoToModelList(List<PersonDto> personsDto) {
		return personsDto.stream()
				.map(PersonMapper::dtoToModel)
				.toList();
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

	public static Person dtoToModelLight(PersonDto personDto) {
		return Person.builder()
				.personId(personDto.getPersonId())
				.build();
	}
}