package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialGoal {
	private UUID financialGoalId;
	private String name;
	private BigDecimal targetAmount;
	private LocalDate endDate;
	private Person creator;
	private LocalDateTime createDate;
}