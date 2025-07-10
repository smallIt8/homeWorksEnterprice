package org.example.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.example.util.constant.ErrorMessageConstant.ERROR_CREATION_FINANCIAL_GOAL_MESSAGE;

@Data

public class FinancialGoal {
    private UUID financialGoalId;
    private String financialGoalName;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private LocalDate targetDate;
    private Person person;

    private FinancialGoal(UUID financialGoalId, String financialGoalName, BigDecimal targetAmount, BigDecimal currentAmount, LocalDate targetDate, Person person) {
        if (targetAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(ERROR_CREATION_FINANCIAL_GOAL_MESSAGE);
        }
        this.financialGoalId = financialGoalId;
        this.financialGoalName = financialGoalName;
        this.targetAmount = targetAmount;
        if (currentAmount == null) {
            this.currentAmount = BigDecimal.ZERO;
        } else {
            this.currentAmount = currentAmount;
        }
        this.targetDate = targetDate;
        this.person = person;
    }

    public static FinancialGoal ofGoal(String financialGoalName, BigDecimal targetAmount, BigDecimal currentAmount, LocalDate targetDate, Person person) {
        return new FinancialGoal(
                UUID.randomUUID(),
                financialGoalName,
                targetAmount,
                currentAmount,
                targetDate,
                person
        );
    }
}