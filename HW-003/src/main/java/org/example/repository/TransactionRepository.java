package org.example.repository;

import org.example.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends ComponentRepository<Transaction, UUID> {

}