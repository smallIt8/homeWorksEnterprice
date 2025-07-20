package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Budget;
import org.example.model.Person;
import org.example.repository.BudgetRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor

public class BudgetServiceImpl implements BudgetService {
	private final BudgetRepository budgetRepository;

	@Override
	public void create() {

	}

	@Override
	public void createBatch() {

	}

	@Override
	public Optional<Budget> update(UUID value) {
		return Optional.empty();
	}

	@Override
	public List<Budget> getAll() {
		return List.of();
	}

	@Override
	public List<Budget> getAllByPerson(Person person) {
		return List.of();
	}

	@Override
	public List<Budget> getAllSortByDate(UUID currentPerson) {
		return List.of();
	}

	@Override
	public List<Budget> getAllSortByCategory(String category, UUID currentPerson) {
		return List.of();
	}

	@Override
	public List<Budget> getAllSortByCategoryAndDate(String category, UUID currentPerson) {
		return List.of();
	}

	@Override
	public List<Budget> getAllSortByCreateDate() {
		return List.of();
	}

	@Override
	public void delete(UUID value) {

	}
}