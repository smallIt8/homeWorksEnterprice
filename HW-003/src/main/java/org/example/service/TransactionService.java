package org.example.service;

import org.example.dto.CategoryDto;
import org.example.dto.PersonDto;
import org.example.dto.TransactionDto;
import org.example.model.Transaction;
import org.example.model.TransactionType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransactionService extends ComponentService<TransactionDto, Transaction, PersonDto, UUID> {

	List<TransactionDto> getAllSortByType(TransactionType type, PersonDto currentPersonDto);

	List<TransactionDto> getAllSortByTypeAndCategory(TransactionType type, CategoryDto categoryDto, PersonDto currentPersonDto);

	List<TransactionDto> getAllSortByTypeAndDate(TransactionType type, LocalDate date, PersonDto currentPersonDto);

	List<TransactionDto> getAllSortByCreateDate(LocalDate createDate, PersonDto currentPersonDto);

}