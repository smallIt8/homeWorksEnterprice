package org.example.service;

import org.example.model.Transaction;
import org.example.model.TransactionType;

import java.util.List;
import java.util.UUID;

public interface TransactionService extends ComponentService<Transaction, UUID> {

	List<Transaction> getAllSortByType(TransactionType type, UUID currentPerson);

	List<Transaction> getAllSortByTypeAndCategory(TransactionType type, UUID currentPerson);

	List<Transaction> getAllSortByTypeAndDate(TransactionType type, UUID currentPerson);

	List<Transaction> getAllSortByCreateDate();
}