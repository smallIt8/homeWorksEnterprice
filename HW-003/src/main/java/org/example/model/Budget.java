package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.YearMonth;
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
	private YearMonth period;
	private Person person;

	@Override
	public String toString() {
		return "Название: " + INDIGO + budgetName + RESET + "\n" +
				category +
				"Лимит: " + INDIGO + limit + RESET + "\n" +
				"Период: " + INDIGO + period + RESET + "\n" +
				"Владелец: " + person.toNameString();
	}
}