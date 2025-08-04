package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.PersonDto;
import org.example.model.FinancialGoal;
import org.example.model.Person;
import org.example.repository.FinancialGoalRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class FinancialGoalServiceImpl implements FinancialGoalService {

	private final FinancialGoalRepository financialGoalRepository;

	@Override
	public void create(PersonDto personDto) {

	}

	@Override
	public void createBatch() {

	}

	@Override
	public Optional<FinancialGoal> update(PersonDto entity) {
		return Optional.empty();
	}

	@Override
	public List<FinancialGoal> getAll() {
		return List.of();
	}

	@Override
	public List<FinancialGoal> getAllSortByEndDate(LocalDate endDate) {
		return List.of();
	}

	@Override
	public List<FinancialGoal> getAllByOwner(PersonDto currentPerson) {
		return List.of();
	}

	@Override
	public void delete(UUID value) {

	}
}