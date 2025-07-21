package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.model.Transaction;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor

public class TransactionRepositoryImpl implements TransactionRepository {
	@Override
	public void create(Transaction value) {

	}

	@Override
	public Optional<Transaction> getById(UUID value) {
		return Optional.empty();
	}

	@Override
	public void update(Transaction value) {

	}

	@Override
	public void delete(UUID value) {

	}
}
