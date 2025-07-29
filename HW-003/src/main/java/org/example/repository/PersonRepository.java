package org.example.repository;

import org.example.model.Family;
import org.example.model.Person;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends Repository<Person, UUID> {

	Optional<Person> entry(String userName);

	boolean checkUserName(String userName);

	boolean checkEmail(String email);

	boolean checkUpdateEmail(String email, UUID currentPersonId);

	void updatePassword(Person person);

	void updatePersonFamily(Person person, Family family);

}