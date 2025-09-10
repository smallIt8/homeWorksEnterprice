package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDto {
	private UUID budgetId;
	private String name;
	private CategoryDto categoryDto;
	private BigDecimal limit;
	private YearMonth period;
	private PersonDto creatorDto;
}