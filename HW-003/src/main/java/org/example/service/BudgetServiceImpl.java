package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.BudgetDto;
import org.example.dto.PersonDto;
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
	public void create(PersonDto personDto) {

	}

	@Override
	public void createBatch() {

	}

	@Override
	public Optional<BudgetDto> update(PersonDto currentPersonDto) {
		return Optional.empty();
	}

	@Override
	public List<BudgetDto> getAll() {
		return List.of();
	}

	@Override
	public List<BudgetDto> getAllByOwner(PersonDto entity) {
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
	public void delete(PersonDto entityDto) {

	}

	@Override
	public void delete(PersonDto currentPersonDto, BudgetDto budgetDto) {

	}
}