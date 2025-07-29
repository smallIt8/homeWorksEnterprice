package org.example.service;

import org.example.model.Family;
import org.example.model.Person;

import java.util.Optional;
import java.util.UUID;

public interface PersonService extends Service<Person, Person, UUID> {

	Optional<Person> entry();

	Optional<Person> getById(UUID currentPerson);

	Optional<Person> updatePassword(Person currentPerson);

	void updateFamily(Person person, Family family);

}