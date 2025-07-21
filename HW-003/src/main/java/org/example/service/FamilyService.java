package org.example.service;

import org.example.model.Family;
import org.example.model.Person;

import java.util.Optional;
import java.util.UUID;

public interface FamilyService extends ComponentService<Family, Person, UUID> {

	void create(Person currentPerson);

	Optional<Family> joinFamily(Person person, Family family);

	boolean addMember(String email, UUID person);

	boolean exitFamily(Person person);
}
