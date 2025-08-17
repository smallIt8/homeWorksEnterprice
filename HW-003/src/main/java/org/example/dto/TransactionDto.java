package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Category;
import org.example.model.Person;
import org.example.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.example.util.constant.ColorsConstant.INDIGO;
import static org.example.util.constant.ColorsConstant.RESET;

@Data
@NoArgsConstructor
public class TransactionDto {
	private UUID transactionId;
	private String transactionName;
	private TransactionType type;
	private Category category;
	private BigDecimal amount;
	private Person person;
	private LocalDate transactionDate;
	private LocalDateTime createDate;

	@Override
	public String toString() {
		return "Наименование операции: " + INDIGO + transactionName + RESET + "\n" +
				"Тип: " + INDIGO + type + RESET + "\n" +
				"Категория: " + INDIGO + category + RESET + "\n" +
				"Сумма: " + INDIGO + amount + RESET + "\n" +
				"Зарегистрировал: " + person.toNameString() +
				"Дата создания: " + INDIGO + createDate + RESET + "\n";
	}
}
