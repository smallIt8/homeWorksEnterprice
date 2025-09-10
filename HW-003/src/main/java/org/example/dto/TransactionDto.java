package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
	private UUID transactionId;
	private String name;
	private TransactionType type;
	private CategoryDto categoryDto;
	private BigDecimal amount;
	private PersonDto creatorDto;
	private LocalDate transactionDate;
	private LocalDateTime createDate;
}