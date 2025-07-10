package org.example.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.example.util.constant.ErrorMessageConstant.ERROR_CREATION_BUDGET_MESSAGE;

@Data

public class Budget {
    private UUID budgetId;
    private Category category;
    private BigDecimal amount;
    private LocalDate startDate;
    private LocalDate endDate;
    private Person person;

    private Budget(UUID BudgetId, Category category, BigDecimal amount, LocalDate startDate, LocalDate endDate, Person person) {
        if (category.getType() != TransactionType.EXPENSE) {
            throw new IllegalArgumentException(ERROR_CREATION_BUDGET_MESSAGE);
        }
        this.budgetId = BudgetId;
        this.category = category;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.person = person;
    }

    public static Budget ofExpenseCategory(Category category, BigDecimal amount, LocalDate startDate, LocalDate endDate, Person person) {
        return new Budget(
                UUID.randomUUID(),
                category,
                amount,
                startDate,
                endDate,
                person);
    }
}