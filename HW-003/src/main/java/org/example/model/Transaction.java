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
public class Transaction {
	private UUID transactionId;
	private String name;
	private TransactionType type;
	private Category category;
	private BigDecimal amount;
	private Person creator;
	private LocalDate transactionDate;
	private LocalDateTime createDate;
}