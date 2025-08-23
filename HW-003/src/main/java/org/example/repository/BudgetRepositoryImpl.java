package org.example.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Budget;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class BudgetRepositoryImpl implements BudgetRepository {

	@Override
	public void create(Budget budget) {

	}

	@Override
	public void createBatch(List<Budget> budgets) {

	}

	@Override
	public void update(Budget budget) {

	}

	@Override
	public List<Budget> findAll(UUID currentPersonId) {
		return List.of();
	}


	@Override
	public Optional<Budget> findById(UUID budget) {
		return Optional.empty();
	}


	@Override
	public void delete(UUID budgetId, UUID creatorId) {

	}
}