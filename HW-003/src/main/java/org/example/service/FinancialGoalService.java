package org.example.service;

import org.example.model.FinancialGoal;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface FinancialGoalService extends ComponentService<FinancialGoal, UUID> {

	List<FinancialGoal> getAllSortByEndDate(LocalDate endDate);
}