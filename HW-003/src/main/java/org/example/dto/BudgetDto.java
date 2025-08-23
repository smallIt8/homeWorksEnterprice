package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Category;
import org.example.model.Person;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

import static org.example.util.constant.ColorsConstant.INDIGO;
import static org.example.util.constant.ColorsConstant.RESET;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDto {
	private UUID budgetId;
	private String name;
	private Category category;
	private BigDecimal limit;
	private YearMonth period;
	private Person creator;

	@Override
	public String toString() {
		return "Название: " + INDIGO + name + RESET + "\n" +
				category +
				"Лимит: " + INDIGO + limit + RESET + "\n" +
				"Период: " + INDIGO + period + RESET + "\n" +
				"Владелец: " + creator.toNameString();
	}
}