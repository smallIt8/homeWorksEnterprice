package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.model.FinancialGoal;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor

public class FinancialGoalRepositoryImpl implements FinancialGoalRepository {
	@Override
	public void create(FinancialGoal value) {

	}

	@Override
	public Optional<FinancialGoal> getById(UUID value) {
		return Optional.empty();
	}

	@Override
	public void update(FinancialGoal value) {

	}

	@Override
	public void delete(UUID value) {

	}
}
