package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.example.util.constant.ColorsConstant.INDIGO;
import static org.example.util.constant.ColorsConstant.RESET;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FinancialGoal {
	private UUID financialGoalId;
	private String financialGoalName;
	private BigDecimal targetAmount;
	private BigDecimal currentAmount;
	private LocalDate endDate;
	private Status status;
	private LocalDate createDate;
	private Person person;

	@Override
	public String toString() {
		return "Название: " + INDIGO + financialGoalName + RESET + "\n" +
				"Цель: " + INDIGO + targetAmount + RESET + "\n" +
				"Накоплено с: " + INDIGO + createDate + RESET + "- " + INDIGO + currentAmount + RESET + "\n" +
				"Конечная дата накопления: " + INDIGO + endDate + RESET + "\n" +
				"Владелец: " + person.toNameString();
	}
}