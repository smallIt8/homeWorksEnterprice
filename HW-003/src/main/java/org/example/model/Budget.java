package org.example.model;

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
public class Budget {
	private UUID budgetId;
	private String name;
	private Category category;
	private BigDecimal limit;
	private YearMonth period;
	private Person creator;
}