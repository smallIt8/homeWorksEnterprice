package org.example.repository;

import org.example.model.Budget;
import org.example.model.Person;

import java.util.List;
import java.util.UUID;

public interface BudgetRepository extends Repository<Budget, UUID> {

	void createBatch(List<Budget> UUIDS);

	List<Budget> getAll();

	List<Person> getByLastName(String lastName);

	List<Person> getAllByCreateDate();

	boolean checkEmail(String email, UUID excludeId);
}
