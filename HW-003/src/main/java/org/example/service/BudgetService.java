package org.example.service;

import org.example.dto.BudgetDto;
import org.example.dto.PersonDto;
import org.example.model.Budget;
import org.example.model.Person;

import java.util.List;
import java.util.UUID;

public interface BudgetService extends ComponentService<BudgetDto, PersonDto, UUID> {

	List<Budget> getAllSortByDate(UUID currentPerson);

	List<Budget> getAllSortByCategory(String category, UUID currentPerson);

	List<Budget> getAllSortByCategoryAndDate(String category, UUID currentPerson);

	List<Budget> getAllSortByCreateDate();

	void delete(PersonDto currentPersonDto, BudgetDto budgetDto);
}