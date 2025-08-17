package org.example.mapper;

import org.example.dto.TransactionDto;
import org.example.model.Transaction;

public class TransactionMapper {
	public static TransactionDto modelToDto(Transaction transaction) {
		return new TransactionDto(
				transaction.getTransactionId(),
				transaction.getTransactionName(),
				transaction.getType(),
				transaction.getCategory(),
				transaction.getAmount(),
				transaction.getPerson(),
				transaction.getTransactionDate(),
				transaction.getCreateDate());
	}

	public static Transaction dtoToModel(TransactionDto transactionDtoDto) {
		return new Transaction(
				transactionDtoDto.getTransactionId(),
				transactionDtoDto.getTransactionName(),
				transactionDtoDto.getType(),
				transactionDtoDto.getCategory(),
				transactionDtoDto.getAmount(),
				transactionDtoDto.getPerson(),
				transactionDtoDto.getTransactionDate(),
				transactionDtoDto.getCreateDate());
	}
}