package org.example.service;

import org.example.model.Budget;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FinancialGoalServiceImpl implements BudgetService {
    @Override
    public void create() {

    }

    @Override
    public void createBatch() {

    }

    @Override
    public List<Budget> getAll() {
        return List.of();
    }

    @Override
    public void getByCategory(String category) {

    }

    @Override
    public void getByStartDate(LocalDate startDate) {

    }

    @Override
    public void getByPerson(String person) {

    }

    @Override
    public Optional<Budget> getById(UUID value) {
        return Optional.empty();
    }

    @Override
    public Optional<Budget> updateById(UUID value) {
        return Optional.empty();
    }

    @Override
    public void delete(UUID value) {

    }
}