package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.model.Budget;
import org.example.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor

public class BudgetRepositoryImpl implements BudgetRepository {

	@Override
	public void create(Budget value) {

	}

	@Override
	public void createBatch(List<Budget> UUIDS) {

	}

	@Override
	public void update(Budget value) {

	}

	@Override
	public void delete(UUID value) {

	}

	@Override
	public List<Budget> getAll() {
		return List.of();
	}

	@Override
	public List<Person> getByLastName(String lastName) {
		return List.of();
	}

	@Override
	public Optional<Budget> getById(UUID value) {
		return Optional.empty();
	}

	@Override
	public List<Person> getAllByCreateDate() {
		return List.of();
	}

	@Override
	public boolean checkEmail(String email, UUID excludeId) {
		return false;
	}

}
