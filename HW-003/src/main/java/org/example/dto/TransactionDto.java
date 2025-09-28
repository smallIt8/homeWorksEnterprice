package org.example.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.RegexConstant.TRANSACTION_NAME_REGEX;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

	private UUID transactionId;

	@NotBlank(message = WARNING_TRANSACTION_NAME_NOT_NULL_MESSAGE)
	@Pattern(regexp = TRANSACTION_NAME_REGEX,
			message = WARNING_ENTER_TRANSACTION_NAME_MESSAGE
	)
	private String transactionName;
	private TransactionType type;

	@NotNull(message = WARNING_SELECT_TRANSACTION_CATEGORY_NOT_NULL_MESSAGE)
	private CategoryDto categoryDto;

	@NotNull(message = WARNING_TRANSACTION_AMOUNT_NOT_NULL_MESSAGE)
	@DecimalMin(value = "0.01", message = WARNING_ENTER_TRANSACTION_AMOUNT_MESSAGE)
	private BigDecimal amount;
	private PersonDto creatorDto;

	@NotNull(message = WARNING_TRANSACTION_DATE_NOT_NULL_MESSAGE)
	@PastOrPresent(message = WARNING_TRANSACTION_DATE_NOT_CURRENT_OR_PAST_MESSAGE)
	private LocalDate transactionDate;
	private LocalDateTime createDate;
}
