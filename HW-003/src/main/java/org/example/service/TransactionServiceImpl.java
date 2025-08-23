package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.CategoryDto;
import org.example.dto.PersonDto;
import org.example.dto.TransactionDto;
import org.example.model.Category;
import org.example.model.Person;
import org.example.model.Transaction;
import org.example.model.TransactionType;
import org.example.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.example.mapper.PersonMapper.*;
import static org.example.mapper.TransactionMapper.*;
import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.InfoMessageConstant.*;
import static org.example.util.constant.RegexConstant.*;

@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;

	@Override
	public void create(TransactionDto transactionDto) {
		Transaction transaction = dtoToModel(transactionDto);

		log.info("Добавление новой транзакции пользователя с ID: '{}'",
				 transaction.getCreator().getPersonId());

		String name = validateName(transaction.getName());
		TransactionType type = validateType(transaction.getType());
		Category category = validateCategory(transaction.getCategory());
		BigDecimal amount = validateAmount(transaction.getAmount());
		LocalDate transactionDate = validateTransactionDate(transaction.getTransactionDate());

		Transaction inputTransaction = Transaction.builder()
				.transactionId(UUID.randomUUID())
				.name(name.toLowerCase())
				.type(type)
				.category(category)
				.amount(amount)
				.creator(transaction.getCreator())
				.transactionDate(transactionDate)
				.build();

		log.info("Создана подготовленная модель добавляемой транзакции '{}' пользователя: '{}'",
				 name,
				 transaction.getCreator().getPersonId());
		try {
			transactionRepository.create(inputTransaction);
			log.info("Транзакция с ID: '{}' успешно создана для пользователя c ID: '{}'",
					 inputTransaction.getTransactionId(),
					 transaction.getCreator().getPersonId());
		} catch (RuntimeException e) {
			log.error("Ошибка при создании транзакции: '{}'",
					  e.getMessage(), e);
			throw e;
		}
	}

	private String validateName(String name) {
		log.info("Создание имени транзакции");
		if (name.matches(TRANSACTION_NAME_REGEX)) {
			log.info("Имя транзакции успешно создано: '{}'",
					 name);
			return name;
		} else {
			log.error("Неверный формат имени: '{}'",
					  name);
			throw new IllegalArgumentException(ERROR_ENTER_TRANSACTION_NAME_MESSAGE);
		}
	}

	private TransactionType validateType(TransactionType type) {
		log.info("Проверка типа транзакции");
		if (type == null) {
			log.error("Тип транзакции не может быть пустым");
			throw new IllegalArgumentException(ERROR_SELECT_TRANSACTION_TYPE_MESSAGE);
		}
		log.info("Тип транзакции успешно проверен: '{}'",
				 type);
		return type;
	}

	private Category validateCategory(Category category) {
		log.info("Проверка категории транзакции");
		if (category == null) {
			log.error("Категория в транзакции не может быть пустой");
			throw new IllegalArgumentException(ERROR_SELECT_TRANSACTION_CATEGORY_MESSAGE);
		}
		// Проверить истинный тип категории - не может быть приходной
		log.info("Категория транзакции успешно проверена: '{}'",
				 category);
		return category;
	}

	private BigDecimal validateAmount(BigDecimal amount) {
		log.info("Проверка стоимости добавляемой транзакции");
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			log.error("Стоимость транзакции '{}' не может быть пустой, 0 или быть меньше 0",
					  amount);
			throw new IllegalArgumentException(ERROR_CREATION_TRANSACTION_AMOUNT_MESSAGE);
		}
		log.info("Стоимость транзакции успешно проверена: '{}'",
				 amount);
		return amount;
	}

	private LocalDate validateTransactionDate(LocalDate transactionDate) {
		log.info("Проверка даты транзакции");
		if (transactionDate == null || transactionDate.isAfter(LocalDate.now())) {
			log.error("Дата транзакции '{}' не может быть пустой и не может быть позже текущей даты",
					  transactionDate);
			throw new IllegalArgumentException(ERROR_CREATION_TRANSACTION_DATE_MESSAGE);
		}
		log.info("Дата транзакции успешно проверена: '{}'",
				 transactionDate);
		return transactionDate;
	}

	@Override
	public Map<TransactionDto, String> createBatch(List<TransactionDto> transactionsDto) {
		log.info("Добавление пакета транзакций: количество = '{}'",
				 transactionsDto.size());

		Map<TransactionDto, String> errors = new LinkedHashMap<>();

		for (TransactionDto dto : transactionsDto) {
			try {
				create(dto);
			} catch (RuntimeException e) {
				log.warn("Ошибка при создании транзакции '{}' в пакете: '{}'",
						 dto.getName(),
						 e.getMessage());
				errors.put(dto, e.getMessage());
			}
		}
		log.info("Пакет транзакций обработан, количество ошибок: {}",
				 errors.size());
		return errors;
		// Not implements List entity to repository
	}

//	@Override
//	public void createBatch() {
//		log.info("Запуск пакетного добавления сотрудников");
//		System.out.println(ADDING_BATCH_MESSAGE);
//		List<Person> persons = new ArrayList<>();
//		while (true) {
//			System.out.println(ADDING_MESSAGE + (persons.size() + 1));
//			createFirstName();
//			createLastName();
//			createEmail();
//			createSalary();
//			createDepartment();
//			Person person = new Person(
//					UUID.randomUUID(),
//					firstName.toUpperCase(),
//					lastName.toUpperCase(),
//					email.toLowerCase(),
//					salary,
//					department
//			);
//			persons.add(person);
//			log.debug("Добавлен сотрудник в пакет: {}", person);
//
//			for (int i = 0; i < AppUtil.ITERATION_LOOP; i++) {
//				System.out.print(ADDING_PERSON);
//				String answer = SCANNER.nextLine().toUpperCase();
//				log.debug("Ввод для продолжения пакетного добавления: '{}'", answer);
//				if (answer.matches(RegexConstant.YES_OR_NO_REGEX)) {
//					if (answer.equals("Y")) {
//						log.info("Выбран ввод следующего сотрудников");
//						break;
//					} else if (answer.equals("N")) {
//						log.info("Не выбран ввод следующего сотрудника");
//						try {
//							personRepository.createBatch(persons);
//							log.info("Пакет сотрудников успешно добавлен. Всего: {}", persons.size());
//							System.out.println(ADDED_PERSONS_MESSAGE);
//						} catch (RuntimeException e) {
//							log.error("Ошибка при пакетном добавлении сотрудников: {}", e.getMessage(), e);
//							throw e;
//						}
//						return;
//					}
//				} else if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
//					System.out.println(ERROR_ENTER_YES_OR_NO_MESSAGE);
//					log.warn("Неверный ввод ответа: {}", answer);
//				} else {
//					log.error("Превышено количество попыток ввода Y/N");
//					AppUtil.exitByFromAttempt();
//				}
//			}
//		}
//	}

	@Override
	public Optional<Transaction> findById(UUID transactionId, UUID currentPersonId) {
		log.info("Получение данных транзакции по ID: '{}'",
				 transactionId);
		try {
			Optional<Transaction> transactionOpt = transactionRepository.findById(transactionId);
			if (transactionOpt.isEmpty()) {
				log.warn("Транзакция с ID: '{}' не найдена",
						 transactionId);
				throw new IllegalArgumentException(NOT_FOUND_TRANSACTION_MESSAGE);
			}
			if (!transactionOpt.get().getCreator().getPersonId().equals(currentPersonId)) {
				log.warn("Транзакция с ID: '{}' не принадлежит пользователю с ID: '{}'",
						 transactionId,
						 currentPersonId);
				throw new SecurityException(ERROR_ACCESS_TRANSACTION_MESSAGE);
			}
			return transactionOpt;
		} catch (RuntimeException e) {
			log.error("Ошибка при получении данных транзакции по ID '{}': '{}'",
					  transactionId,
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<TransactionDto> findAll(PersonDto currentPersonDto) {
		log.info("Получение списка транзакций пользователя с ID: '{}'",
				 currentPersonDto.getPersonId());
		try {
			List<Transaction> transactions = transactionRepository.findAll(currentPersonDto.getPersonId());
			if (transactions.isEmpty()) {
				log.info("Список транзакций пользователя с ID '{}' пуст",
						 currentPersonDto.getPersonId());
				throw new IllegalArgumentException(EMPTY_LIST_TRANSACTION_BY_PERSON_MESSAGE);
			}
			log.info("Получено '{}' транзакций пользователя с ID '{}':",
					 transactions.size(),
					 currentPersonDto.getPersonId());
			return modelToDtoList(transactions);
		} catch (RuntimeException e) {
			log.error("Ошибка при получении списка транзакций пользователя '{}': '{}'",
					  currentPersonDto.getPersonId(),
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Optional<TransactionDto> update(TransactionDto transactionDto, PersonDto currentPersonDto) {
		Transaction transaction = dtoToModel(transactionDto);
		Person person = dtoToModel(currentPersonDto);

		log.info("Обновление транзакции с ID: '{}' пользователя с ID: '{}'",
				 transaction.getTransactionId(),
				 person.getPersonId());

		Transaction transactionUpdate = findById(
				transaction.getTransactionId(),
				person.getPersonId()).orElseThrow(() -> {
					log.warn("Не удалось обновить транзакцию с ID '{}' - транзакция не найдена",
							 transaction.getTransactionId());
					return new IllegalArgumentException(NOT_FOUND_TRANSACTION_MESSAGE);
		});

		log.info("Обновление данных транзакции с ID: '{}' пользователя с ID: '{}'",
				 transactionUpdate.getTransactionId(),
				 person.getPersonId());

		String name = validateName(transaction.getName());
		TransactionType type = validateType(transaction.getType());
		Category category = validateCategory(transaction.getCategory());
		BigDecimal amount = validateAmount(transaction.getAmount());
		LocalDate transactionDate = validateTransactionDate(transaction.getTransactionDate());

		transactionUpdate.setName(name);
		transactionUpdate.setType(type);
		transactionUpdate.setCategory(category);
		transactionUpdate.setAmount(amount);
		transactionUpdate.setTransactionDate(transactionDate);

		log.info("Создана подготовленная модель обновляемой транзакции с ID: '{}'",
				 transactionUpdate.getTransactionId());
		try {
			transactionRepository.update(transactionUpdate);
			log.info("Транзакция с ID '{}' успешно обновлена",
					 transactionUpdate.getTransactionId());
			return Optional.of(modelToDto(transactionUpdate));
		} catch (RuntimeException e) {
			log.error("Ошибка при обновлении транзакции по ID '{}': '{}'",
					  transactionUpdate.getTransactionId(),
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<TransactionDto> getAllSortByType(TransactionType type, PersonDto currentPersonDto) {
		log.info("Coming soon.");
		return List.of();
	}

	@Override
	public List<TransactionDto> getAllSortByTypeAndCategory(TransactionType type, CategoryDto categoryDto, PersonDto currentPersonDto) {
		log.info("Coming soon..");
		return List.of();
	}

	@Override
	public List<TransactionDto> getAllSortByTypeAndDate(TransactionType type, LocalDate date, PersonDto currentPersonDto) {
		log.info("Coming soon....");
		return List.of();
	}

	@Override
	public List<TransactionDto> getAllSortByCreateDate(LocalDate createDate, PersonDto currentPersonDto) {
		log.info("Coming soon.....");
		return List.of();
	}

	@Override
	public void delete(TransactionDto transactionDto, PersonDto currentPersonDto) {
		Transaction transaction = dtoToModel(transactionDto);
		Person person = dtoToModel(currentPersonDto);

		log.info("Удаление транзакции с ID: '{}' пользователя с ID: '{}'",
				 transaction.getTransactionId(),
				 person.getPersonId());

		Transaction transactionToDelete = findById(
				transaction.getTransactionId(),
				person.getPersonId()).orElseThrow(() -> {
					log.warn("Не удалось удалить транзакцию с ID '{}' - транзакция не найдена",
							 transaction.getTransactionId());
					return new IllegalArgumentException(NOT_FOUND_TRANSACTION_MESSAGE);
		});

		try {
			transactionRepository.delete(transactionToDelete.getTransactionId(), person.getPersonId());
			log.info("Данные транзакции с ID '{}' удалены",
					 transactionToDelete.getTransactionId());
		} catch (RuntimeException e) {
			log.error("Ошибка при удалении данных транзакции с ID '{}': '{}'",
					  transactionToDelete.getTransactionId(),
					  e.getMessage(), e);
			throw e;
		}
	}
}