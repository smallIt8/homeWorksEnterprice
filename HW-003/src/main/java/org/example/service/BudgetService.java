package org.example.service;

import org.example.model.Budget;

import java.time.LocalDate;
import java.util.UUID;

public interface BudgetService extends Service<Budget, UUID> {

    void getByCategory(String category);

    void getByStartDate(LocalDate startDate); //DateID?

    void getByPerson(String person); //PersonID?
}
