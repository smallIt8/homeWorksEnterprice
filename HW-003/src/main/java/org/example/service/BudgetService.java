package org.example.service;

import org.example.model.Budget;
import org.example.model.Person;

import java.util.List;
import java.util.UUID;

public interface BudgetService extends ComponentService<Budget, Person, UUID> {

	List<Budget> getAllSortByDate(UUID currentPerson);

	List<Budget> getAllSortByCategory(String category, UUID currentPerson);

	List<Budget> getAllSortByCategoryAndDate(String category, UUID currentPerson);

	List<Budget> getAllSortByCreateDate();
}