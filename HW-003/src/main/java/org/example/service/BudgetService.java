package org.example.service;

import org.example.model.Budget;

import java.util.List;
import java.util.UUID;

public interface BudgetService extends ComponentService<Budget, UUID> {

	List<Budget> getAllSortByDate(UUID currentPerson);

	List<Budget> getAllSortByCategory(String category, UUID currentPerson);

	List<Budget> getAllSortByCategoryAndDate(String category, UUID currentPerson);

	List<Budget> getAllSortByCreateDate();
}