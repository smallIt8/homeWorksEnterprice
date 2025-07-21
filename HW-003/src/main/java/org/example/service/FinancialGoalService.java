package org.example.service;

import org.example.model.FinancialGoal;
import org.example.model.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface FinancialGoalService extends ComponentService<FinancialGoal, Person, UUID> {

	List<FinancialGoal> getAllSortByEndDate(LocalDate endDate);
}