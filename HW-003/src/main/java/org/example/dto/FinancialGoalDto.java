package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Person;
import org.example.model.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.example.util.constant.ColorsConstant.INDIGO;
import static org.example.util.constant.ColorsConstant.RESET;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialGoalDto {
	private UUID financialGoalId;
	private String name;
	private BigDecimal targetAmount;
	private BigDecimal currentAmount; // null in table
	private LocalDate endDate;
	private Status status; // null in table
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
