package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PersonDto;
import org.example.dto.TransactionDto;
import org.example.mapper.PersonMapper;
import org.example.mapper.TransactionMapper;
import org.example.model.Transaction;
import org.example.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.example.util.ValidationDtoUtil.validateAnnotation;
import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.InfoMessageConstant.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;
	private final TransactionMapper transactionMapper;
	private final PersonMapper personMapper;

	@Override
	public void create(TransactionDto transactionDto) {
		log.debug("Добавление новой транзакции пользователя с ID: '{}'", transactionDto.getCreatorDto().getPersonId());
		var inputTransaction = buildTransaction(transactionDto);

		final var transaction = transactionMapper.mapDtoToModel(inputTransaction);
		log.info("Создана подготовленная модель добавляемой транзакции '{}' пользователя: '{}'",
				 transaction.getTransactionName(),
				 transaction.getCreator().getPersonId());
		try {
			transactionRepository.create(transaction);
			log.debug("Транзакция с ID: '{}' успешно создана для пользователя c ID: '{}'",
					 transaction.getTransactionId(),
					 transaction.getCreator().getPersonId());
		} catch (RuntimeException e) {
			log.error("Ошибка при создании транзакции: '{}'", e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Map<TransactionDto, String> createBatch(List<TransactionDto> transactionsDto) {
		log.debug("Добавление пакета транзакций: количество = '{}'", transactionsDto.size());
		Map<TransactionDto, String> errors = new LinkedHashMap<>();
		List<Transaction> transactionsSuccess = new ArrayList<>();

		for (var transactionDto : transactionsDto) {
			try {
				var validTransaction = buildTransaction(transactionDto);
				transactionsSuccess.add(transactionMapper.mapDtoToModel(validTransaction));
				log.debug("Транзакция с ID: '{}' успешно подготовлена для добавления", validTransaction.getTransactionId());
			} catch (RuntimeException e) {
				log.error("Ошибка при создании транзакции '{}' в пакете: '{}'",
						 transactionDto.getTransactionName(),
						 e.getMessage());
				errors.put(transactionDto, e.getMessage());
			}
		}
		if (!transactionsSuccess.isEmpty()) {
			transactionRepository.createBatch(transactionsSuccess);
			log.info("Успешно добавленные транзакции: '{}'", transactionsSuccess.size());
		}
		log.info("Пакет транзакций обработан, успешно: '{}',  количество ошибок: '{}'",
				 transactionsSuccess.size(),
				 errors.size());
		return errors;
	}

	private TransactionDto buildTransaction(TransactionDto transactionDto) {
		validateAnnotation(transactionDto);

		return TransactionDto.builder()
				.transactionId(UUID.randomUUID())
				.transactionName(transactionDto.getTransactionName())
				.type(transactionDto.getType())
				.categoryDto(transactionDto.getCategoryDto())
				.amount(transactionDto.getAmount())
				.creatorDto(transactionDto.getCreatorDto())
				.transactionDate(transactionDto.getTransactionDate())
				.build();
	}

	@Override
	public Optional<Transaction> findById(UUID transactionId, UUID currentPersonId) {
		log.debug("Получение данных транзакции по ID: '{}'", transactionId);
		try {
			Optional<Transaction> transactionOpt = transactionRepository.findById(transactionId);
			if (transactionOpt.isEmpty()) {
				log.warn("Транзакция с ID: '{}' не найдена", transactionId);
				throw new IllegalArgumentException(NOT_FOUND_TRANSACTION_MESSAGE);
			}
			if (!transactionOpt.get().getCreator().getPersonId().equals(currentPersonId)) {
				log.warn("Транзакция с ID: '{}' не принадлежит пользователю с ID: '{}'", transactionId, currentPersonId);
				throw new SecurityException(ERROR_ACCESS_TRANSACTION_MESSAGE);
			}
			return transactionOpt;
		} catch (RuntimeException e) {
			log.error("Ошибка при получении данных транзакции по ID '{}': '{}'", transactionId, e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<TransactionDto> findAll(PersonDto currentPersonDto) {
		log.debug("Получение списка транзакций пользователя с ID: '{}'", currentPersonDto.getPersonId());
		try {
			List<Transaction> transactions = transactionRepository.findAll(currentPersonDto.getPersonId());
			if (transactions.isEmpty()) {
				log.warn("Список транзакций пользователя с ID '{}' пуст", currentPersonDto.getPersonId());
				throw new IllegalArgumentException(EMPTY_LIST_TRANSACTION_BY_PERSON_MESSAGE);
			}
			log.info("Получено '{}' транзакций пользователя с ID '{}':", transactions.size(), currentPersonDto.getPersonId());
			return transactionMapper.mapModelToDtoList(transactions);
		} catch (RuntimeException e) {
			log.error("Ошибка при получении списка транзакций пользователя '{}': '{}'",
					  currentPersonDto.getPersonId(),
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Optional<TransactionDto> update(TransactionDto transactionDto, PersonDto currentPersonDto) {
		final var transaction = transactionMapper.mapDtoToModel(transactionDto);
		final var person = personMapper.mapDtoToModel(currentPersonDto);

		log.debug("Обновление транзакции с ID: '{}' пользователя с ID: '{}'",
				 transaction.getTransactionId(),
				 person.getPersonId());

		var transactionUpdate = findById(
				transaction.getTransactionId(),
				person.getPersonId()).orElseThrow(() -> {
			log.warn("Не удалось обновить транзакцию с ID '{}' - транзакция не найдена", transaction.getTransactionId());
			return new IllegalArgumentException(NOT_FOUND_TRANSACTION_MESSAGE);
		});

		log.debug("Обновление данных транзакции с ID: '{}' пользователя с ID: '{}'",
				 transactionUpdate.getTransactionId(),
				 person.getPersonId());

		validateAnnotation(transactionDto);

		transactionUpdate.setTransactionName(transaction.getTransactionName());
		transactionUpdate.setType(transaction.getType());
		transactionUpdate.setCategory(transaction.getCategory());
		transactionUpdate.setAmount(transaction.getAmount());
		transactionUpdate.setTransactionDate(transaction.getTransactionDate());

		log.debug("Создана подготовленная модель обновляемой транзакции с ID: '{}'", transactionUpdate.getTransactionId());
		try {
			transactionRepository.update(transactionUpdate);
			log.info("Транзакция с ID '{}' успешно обновлена", transactionUpdate.getTransactionId());
			return Optional.of(transactionMapper.mapModelToDto(transactionUpdate));
		} catch (RuntimeException e) {
			log.error("Ошибка при обновлении транзакции по ID '{}': '{}'",
					  transactionUpdate.getTransactionId(),
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void delete(TransactionDto transactionDto, PersonDto currentPersonDto) {
		final var transaction = transactionMapper.mapDtoToModelLight(transactionDto);
		final var person = personMapper.mapDtoToModel(currentPersonDto);

		log.debug("Удаление транзакции с ID: '{}' пользователя с ID: '{}'",
				 transaction.getTransactionId(), person.getPersonId());

		var transactionToDelete = findById(
				transaction.getTransactionId(),
				person.getPersonId()).orElseThrow(() -> {
			log.warn("Не удалось удалить транзакцию с ID '{}' - транзакция не найдена", transaction.getTransactionId());
			return new IllegalArgumentException(NOT_FOUND_TRANSACTION_MESSAGE);
		});

		try {
			transactionRepository.delete(transactionToDelete.getTransactionId(), person.getPersonId());
			log.info("Данные транзакции с ID '{}' удалены", transactionToDelete.getTransactionId());
		} catch (RuntimeException e) {
			log.error("Ошибка при удалении данных транзакции с ID '{}': '{}'",
					  transactionToDelete.getTransactionId(),
					  e.getMessage(), e);
			throw e;
		}
	}
}
