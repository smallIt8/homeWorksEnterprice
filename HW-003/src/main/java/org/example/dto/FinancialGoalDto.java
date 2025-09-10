package org.example.dto;

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
public class FinancialGoalDto {
	private UUID financialGoalId;
	private String name;
	private BigDecimal targetAmount;
	private LocalDate endDate;
	private PersonDto creatorDto;
	private LocalDateTime createDate;
}