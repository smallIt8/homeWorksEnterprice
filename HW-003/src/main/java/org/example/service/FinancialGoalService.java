package org.example.service;

import org.example.dto.FinancialGoalDto;
import org.example.dto.PersonDto;
import org.example.model.FinancialGoal;

import java.util.UUID;

public interface FinancialGoalService extends ComponentService<FinancialGoalDto, FinancialGoal, PersonDto, UUID> {
}
