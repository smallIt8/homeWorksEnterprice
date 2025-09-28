package org.example.repository;

import org.example.model.Person;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository {

	Optional<Person> getPersonByUserName(String userName);

	void create(Person person);

	boolean existsByUserName(String userName);

	boolean existsByEmail(String email);

	Optional<Person> findById(UUID personId);

	void update(Person currentPerson);

	boolean updateEmailExistsByExclude(String email, UUID currentPersonId);

	void updatePassword(Person currentPerson);

	void delete(UUID currentPersonId);
}
