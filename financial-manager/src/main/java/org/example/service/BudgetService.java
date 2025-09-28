package org.example.service;

import org.example.dto.BudgetDto;
import org.example.dto.PersonDto;
import org.example.model.Budget;

import java.util.UUID;

public interface BudgetService extends ComponentService<BudgetDto, Budget, PersonDto, UUID> {
}
