package org.example.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {
	@Override
	public void create(Transaction transaction) {

	}

	@Override
	public void createBatch(List<Transaction> transactions) {

	}

	@Override
	public Optional<Transaction> findById(UUID transaction) {
		return Optional.empty();
	}

	@Override
	public void update(Transaction transaction) {

	}

	@Override
	public List<Transaction> findAll(UUID currentPersonId) {
		return List.of();
	}

	@Override
	public void delete(UUID transactionId, UUID creatorId) {

	}
}
