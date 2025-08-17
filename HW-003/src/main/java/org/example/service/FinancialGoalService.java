package org.example.service;

import org.example.dto.FinancialGoalDto;
import org.example.dto.PersonDto;
import org.example.model.FinancialGoal;
import org.example.model.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface FinancialGoalService extends ComponentService<FinancialGoal, PersonDto, UUID> {

	List<FinancialGoal> getAllSortByEndDate(LocalDate endDate);

	void delete(PersonDto currentPersonDto, FinancialGoalDto financialGoalDto);
}