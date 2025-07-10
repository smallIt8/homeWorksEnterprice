package org.example.service;

import org.example.model.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TransactionServiceImpl implements TransactionService {

    @Override
    public void create() {

    }

    @Override
    public void createBatch() {

    }

    @Override
    public List<Transaction> getAll() {
        return List.of();
    }

    @Override
    public void getAllByCreateDate() {

    }

    @Override
    public void getByCategory(String category) {

    }

    @Override
    public void getByType(String type) {

    }

    @Override
    public void getByPerson(String person) { //PersonID?

    }

    @Override
    public Optional<Transaction> getById(UUID value) {
        return Optional.empty();
    }

    @Override
    public Optional<Transaction> updateById(UUID value) {
        return Optional.empty();
    }

    @Override
    public void delete(UUID value) {

    }
}
