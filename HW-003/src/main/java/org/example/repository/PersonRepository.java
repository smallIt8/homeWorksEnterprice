package org.example.repository;

import org.example.model.Family;
import org.example.model.Person;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository {

	Optional<Person> entry(String userName);

	void create(Person person);

	boolean checkUserName(String userName);

	boolean checkEmail(String email);

	Optional<Person> findById(UUID personId);

	void update(Person currentPerson);

	boolean checkUpdateEmail(String email, UUID currentPersonId);

	void updatePassword(Person currentPerson);

	void delete(UUID currentPersonId);

}