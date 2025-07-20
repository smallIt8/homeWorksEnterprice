package org.example.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.example.util.constant.ColorsConstant.INDIGO;
import static org.example.util.constant.ColorsConstant.RESET;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Budget {
	private UUID budgetId;
	private String budgetName;
	private Category category;
	private BigDecimal limit;
	private BigDecimal spent;
	private LocalDate period;
	private Person person;

	@Override
	public String toString() {
		return "Название: " + INDIGO + budgetName + RESET + "\n" +
				category +
				"Лимит: " + INDIGO + limit + RESET + "\n" +
				"Потрачено: " + INDIGO + spent + RESET + "\n" +
				"Период: " + INDIGO + period + RESET + "\n" +
				"Владелец: " + person.toNameString();
	}
}