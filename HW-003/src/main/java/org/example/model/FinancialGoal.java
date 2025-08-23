package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.example.util.constant.ColorsConstant.INDIGO;
import static org.example.util.constant.ColorsConstant.RESET;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialGoal {
	private UUID financialGoalId;
	private String name;
	private BigDecimal targetAmount;
	private BigDecimal currentAmount;
	private LocalDate endDate;
	private Status status;
	private LocalDate createDate;
	private Person creator;

	@Override
	public String toString() {
		return "Название: " + INDIGO + name + RESET + "\n" +
				"Цель: " + INDIGO + targetAmount + RESET + "\n" +
				"Накоплено с: " + INDIGO + createDate + RESET + "- " + INDIGO + currentAmount + RESET + "\n" +
				"Конечная дата накопления: " + INDIGO + endDate + RESET + "\n" +
				"Владелец: " + creator.toNameString();
	}
}