package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Person;
import org.example.model.Transaction;
import org.example.model.TransactionType;
import org.example.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor

public class TransactionServiceImpl implements TransactionService {
	private final TransactionRepository transactionRepository;

	@Override
	public void create() {

	}

	@Override
	public void createBatch() {

	}

	@Override
	public Optional<Transaction> update(UUID value) {
		return Optional.empty();
	}

	@Override
	public List<Transaction> getAll() {
		return List.of();
	}

	@Override
	public List<Transaction> getAllByPerson(Person person) {
		return List.of();
	}

	@Override
	public List<Transaction> getAllSortByType(TransactionType type, UUID currentPerson) {
		return List.of();
	}

	@Override
	public List<Transaction> getAllSortByTypeAndCategory(TransactionType type, UUID currentPerson) {
		return List.of();
	}

	@Override
	public List<Transaction> getAllSortByTypeAndDate(TransactionType type, UUID currentPerson) {
		return List.of();
	}

	@Override
	public List<Transaction> getAllSortByCreateDate() {
		return List.of();
	}

	@Override
	public void delete(UUID value) {

	}
}