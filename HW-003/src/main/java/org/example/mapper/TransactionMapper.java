package org.example.mapper;

import org.example.dto.TransactionDto;
import org.example.model.Transaction;

import java.util.List;

public class TransactionMapper {
	public static List<TransactionDto> modelToDtoList(List<Transaction> transactions) {
		return transactions.stream()
				.map(TransactionMapper::modelToDto)
				.toList();
	}

	public static TransactionDto modelToDto(Transaction transaction) {
		return TransactionDto.builder()
				.transactionId(transaction.getTransactionId())
				.name(transaction.getName())
				.type(transaction.getType())
				.categoryDto(CategoryMapper.modelToDtoLight(transaction.getCategory()))
				.amount(transaction.getAmount())
				.creatorDto(PersonMapper.modelToDtoLight(transaction.getCreator()))
				.transactionDate(transaction.getTransactionDate())
				.build();
	}

	public static List<Transaction> dtoToModelList(List<TransactionDto> transactionsDto) {
		return transactionsDto.stream()
				.map(TransactionMapper::dtoToModel)
				.toList();
	}

	public static Transaction dtoToModel(TransactionDto transactionDto) {
		return Transaction.builder()
				.transactionId(transactionDto.getTransactionId())
				.name(transactionDto.getName())
				.type(transactionDto.getType())
				.category(CategoryMapper.dtoToModelLight(transactionDto.getCategoryDto()))
				.amount(transactionDto.getAmount())
				.creator(PersonMapper.dtoToModelLight(transactionDto.getCreatorDto()))
				.transactionDate(transactionDto.getTransactionDate())
				.build();
	}

	public static Transaction dtoToModelLight(TransactionDto transactionDto) {
		return Transaction.builder()
				.transactionId(transactionDto.getTransactionId())
				.build();
	}
}