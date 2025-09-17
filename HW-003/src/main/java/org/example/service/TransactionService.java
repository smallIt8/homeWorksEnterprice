package org.example.service;

import org.example.dto.PersonDto;
import org.example.dto.TransactionDto;
import org.example.model.Transaction;

import java.util.UUID;

public interface TransactionService extends ComponentService<TransactionDto, Transaction, PersonDto, UUID> {
}
