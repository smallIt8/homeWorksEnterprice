package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.dto.TransactionDto;
import org.example.model.Transaction;

import java.util.List;

@RequiredArgsConstructor
public class TransactionMapper {

	private final CategoryMapper categoryMapper;
	private final PersonMapper personMapper;

	public List<TransactionDto> mapModelToDtoList(List<Transaction> transactions) {
		return transactions.stream()
				.map(this::mapModelToDto)
				.toList();
	}

	public TransactionDto mapModelToDto(Transaction transaction) {
		return TransactionDto.builder()
				.transactionId(transaction.getTransactionId())
				.transactionName(transaction.getTransactionName())
				.type(transaction.getType())
				.categoryDto(categoryMapper.mapModelToDtoLight(transaction.getCategory()))
				.amount(transaction.getAmount())
				.creatorDto(personMapper.mapModelToDtoLight(transaction.getCreator()))
				.transactionDate(transaction.getTransactionDate())
				.build();
	}

	public List<Transaction> mapDtoToModelList(List<TransactionDto> transactionsDto) {
		return transactionsDto.stream()
				.map(this::mapDtoToModel)
				.toList();
	}

	public Transaction mapDtoToModel(TransactionDto transactionDto) {
		return Transaction.builder()
				.transactionId(transactionDto.getTransactionId())
				.transactionName(transactionDto.getTransactionName())
				.type(transactionDto.getType())
				.category(categoryMapper.mapDtoToModelLight(transactionDto.getCategoryDto()))
				.amount(transactionDto.getAmount())
				.creator(personMapper.mapDtoToModelLight(transactionDto.getCreatorDto()))
				.transactionDate(transactionDto.getTransactionDate())
				.build();
	}

	public Transaction mapDtoToModelLight(TransactionDto transactionDto) {
		return Transaction.builder()
				.transactionId(transactionDto.getTransactionId())
				.build();
	}
}
