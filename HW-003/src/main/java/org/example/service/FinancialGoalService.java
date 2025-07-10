package org.example.service;

import org.example.model.FinancialGoal;

import java.time.LocalDate;
import java.util.UUID;

public interface FinancialGoalService extends Service<FinancialGoal, UUID> {

    void getByFinancialGoalName(String financialGoalName);

    void getByTargetDate(LocalDate targetDate);

    void getByPerson(String person); //PersonID?
}