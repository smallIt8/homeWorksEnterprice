package org.example.mapper;

import org.example.dto.TransactionDto;
import org.example.model.Transaction;

import java.util.List;

public class TransactionMapper {
	public static TransactionDto modelToDto(Transaction transaction) {
		return TransactionDto.builder()
				.transactionId(transaction.getTransactionId())
				.name(transaction.getName())
				.type(transaction.getType())
				.category(transaction.getCategory())
				.amount(transaction.getAmount())
				.creator(transaction.getCreator())
				.transactionDate(transaction.getTransactionDate())
				.createDate(transaction.getCreateDate())
				.build();
	}

	public static List<TransactionDto> modelToDtoList(List<Transaction> transactions) {
		return transactions.stream()
				.map(TransactionMapper::modelToDto)
				.toList();
	}

	public static Transaction dtoToModel(TransactionDto transactionDto) {
		return Transaction.builder()
				.transactionId(transactionDto.getTransactionId())
				.name(transactionDto.getName())
				.type(transactionDto.getType())
				.category(transactionDto.getCategory())
				.amount(transactionDto.getAmount())
				.creator(transactionDto.getCreator())
				.transactionDate(transactionDto.getTransactionDate())
				.build();
	}
}