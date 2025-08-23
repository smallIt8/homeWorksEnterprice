package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.example.util.constant.ColorsConstant.INDIGO;
import static org.example.util.constant.ColorsConstant.RESET;

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

	@Override
	public String toString() {
		return "Наименование операции: " + INDIGO + name + RESET + "\n" +
				"Тип: " + INDIGO + type + RESET + "\n" +
				"Категория: " + INDIGO + category + RESET + "\n" +
				"Сумма: " + INDIGO + amount + RESET + "\n" +
				"Зарегистрировал: " + creator.toNameString() +
				"Дата создания: " + INDIGO + createDate + RESET + "\n";
	}
}