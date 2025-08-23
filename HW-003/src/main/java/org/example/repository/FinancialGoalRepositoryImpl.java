package org.example.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.FinancialGoal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class FinancialGoalRepositoryImpl implements FinancialGoalRepository {
	@Override
	public void create(FinancialGoal financialGoal) {

	}

	@Override
	public void createBatch(List<FinancialGoal> financialGoals) {

	}

	@Override
	public Optional<FinancialGoal> findById(UUID financialGoal) {
		return Optional.empty();
	}

	@Override
	public List<FinancialGoal> findAll(UUID currentPersonId) {
		return List.of();
	}

	@Override
	public void update(FinancialGoal financialGoal) {

	}

	@Override
	public void delete(UUID financialGoalId, UUID creatorId) {

	}
}
