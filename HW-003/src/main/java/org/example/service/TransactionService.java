package org.example.service;

import org.example.model.Transaction;

import java.util.UUID;

public interface TransactionService extends Service<Transaction, UUID> {

    void getAllByCreateDate();

    void getByCategory(String category);

    void getByType(String type);

    void getByPerson(String person); //PersonID?
}