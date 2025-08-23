package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PersonDto;
import org.example.dto.FinancialGoalDto;
import org.example.mapper.PersonMapper;
import org.example.model.Person;
import org.example.model.FinancialGoal;
import org.example.repository.FinancialGoalRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.example.mapper.PersonMapper.*;
import static org.example.mapper.FinancialGoalMapper.*;
import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.InfoMessageConstant.*;
import static org.example.util.constant.RegexConstant.*;

@Slf4j
@RequiredArgsConstructor
public class FinancialGoalServiceImpl implements FinancialGoalService {

	private final FinancialGoalRepository financialGoalRepository;

	@Override
	public void create(FinancialGoalDto financialGoalDto) {
		FinancialGoal financialGoal = dtoToModel(financialGoalDto);

		log.info("Добавление новой долгосрочной финансовой цели пользователя с ID: '{}'",
				 financialGoal.getCreator().getPersonId());

		String name = validateName(financialGoal.getName());
		BigDecimal targetAmount = validateTargetAmount(financialGoal.getTargetAmount());
		LocalDate endDate = validateEndDate(financialGoal.getEndDate());

		FinancialGoal inputFinancialGoal = FinancialGoal.builder()
				.financialGoalId(UUID.randomUUID())
				.name(name.toLowerCase())
				.targetAmount(targetAmount)
				.endDate(endDate)
				.creator(financialGoal.getCreator())
				.build();

		log.info("Создана подготовленная модель добавляемой долгосрочной финансовой цели '{}' пользователя: '{}'",
				 name,
				 financialGoal.getCreator().getPersonId());
		try {
			financialGoalRepository.create(inputFinancialGoal);
			log.info("Долгосрочная финансовая цель с ID: '{}' успешно создана для пользователя c ID: '{}'",
					 inputFinancialGoal.getFinancialGoalId(),
					 financialGoal.getCreator().getPersonId());
		} catch (RuntimeException e) {
			log.error("Ошибка при создании долгосрочной финансовой цели: '{}'",
					  e.getMessage(), e);
			throw e;
		}
	}

	private String validateName(String name) {
		log.info("Создание имени долгосрочной финансовой цели");
		if (name.matches(FINANCIAL_GOAL_NAME_REGEX)) {
			log.info("Имя долгосрочной финансовой цели успешно создано: '{}'",
					 name);
			return name;
		} else {
			log.error("Неверный формат имени: '{}'", name);
			throw new IllegalArgumentException(ERROR_ENTER_FINANCIAL_GOAL_NAME_MESSAGE);
		}
	}

	private BigDecimal validateTargetAmount(BigDecimal targetAmount) {
		log.info("Проверка целевой суммы долгосрочной финансовой цели");
		if (targetAmount == null || targetAmount.compareTo(BigDecimal.ZERO) <= 0) {
			log.error("Целевая сумма долгосрочной финансовой цели '{}' не может быть пустой, 0 или быть меньше 0",
					  targetAmount);
			throw new IllegalArgumentException(ERROR_CREATION_FINANCIAL_GOAL_TARGET_AMOUNT_MESSAGE);
		}
		log.info("Целевая сумма долгосрочной финансовой цели успешно проверена: '{}'",
				 targetAmount);
		return targetAmount;
	}

	private LocalDate validateEndDate(LocalDate endDate) {
		log.info("Проверка конечной даты долгосрочной финансовой цели");
		if (endDate == null || endDate.isBefore(LocalDate.now())) {
			log.error("Конечная дата долгосрочной финансовой цели '{}' не может быть пустой и не может быть раньше текущей даты",
					  endDate);
			throw new IllegalArgumentException(ERROR_CREATION_FINANCIAL_GOAL_END_DATE_MESSAGE);
		}
		log.info("Конечная дата долгосрочной финансовой цели успешно проверена: '{}'",
				 endDate);
		return endDate;
	}

	@Override
	public Map<FinancialGoalDto, String> createBatch(List<FinancialGoalDto> financialGoalsDto) {
		log.info("Добавление пакета долгосрочных финансовых целей: количество = '{}'",
				 financialGoalsDto.size());

		Map<FinancialGoalDto, String> errors = new LinkedHashMap<>();

		for (FinancialGoalDto dto : financialGoalsDto) {
			try {
				create(dto);
			} catch (RuntimeException e) {
				log.warn("Ошибка при создании долгосрочной финансовой цели '{}' в пакете: '{}'",
						 dto.getName(),
						 e.getMessage());
				errors.put(dto, e.getMessage());
			}
		}
		log.info("Пакет долгосрочных финансовых целей обработан, количество ошибок: {}",
				 errors.size());
		return errors;
		// Not implements List entity to repository
	}

	@Override
	public Optional<FinancialGoal> findById(UUID financialGoalId, UUID currentPersonId) {
		log.info("Получение данных долгосрочной финансовой цели по ID: '{}'",
				 financialGoalId);
		try {
			Optional<FinancialGoal> financialGoalOpt = financialGoalRepository.findById(financialGoalId);
			if (financialGoalOpt.isEmpty()) {
				log.warn("Долгосрочная финансовая цель с ID: '{}' не найдена",
						 financialGoalId);
				throw new IllegalArgumentException(NOT_FOUND_FINANCIAL_GOAL_MESSAGE);
			}
			if (!financialGoalOpt.get().getCreator().getPersonId().equals(currentPersonId)) {
				log.warn("Долгосрочная финансовая цель с ID: '{}' не принадлежит пользователю с ID: '{}'",
						 financialGoalId,
						 currentPersonId);
				throw new SecurityException(ERROR_ACCESS_FINANCIAL_GOAL_MESSAGE);
			}
			return financialGoalOpt;
		} catch (RuntimeException e) {
			log.error("Ошибка при получении данных долгосрочной финансовой цели по ID '{}': '{}'",
					  financialGoalId,
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<FinancialGoalDto> findAll(PersonDto currentPersonDto) {
		log.info("Получение списка долгосрочных финансовых целей пользователя с ID: '{}'",
				 currentPersonDto.getPersonId());
		try {
			List<FinancialGoal> financialGoals = financialGoalRepository.findAll(currentPersonDto.getPersonId());
			if (financialGoals.isEmpty()) {
				log.info("Список долгосрочных финансовых целей пользователя с ID '{}' пуст",
						 currentPersonDto.getPersonId());
				throw new IllegalArgumentException(EMPTY_LIST_FINANCIAL_GOAL_BY_PERSON_MESSAGE);
			}
			log.info("Получено '{}' долгосрочных финансовых целей пользователя с ID '{}':",
					 financialGoals.size(),
					 currentPersonDto.getPersonId());
			return modelToDtoList(financialGoals);
		} catch (RuntimeException e) {
			log.error("Ошибка при получении списка долгосрочных финансовых целей пользователя '{}': '{}'",
					  currentPersonDto.getPersonId(),
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Optional<FinancialGoalDto> update(FinancialGoalDto financialGoalDto, PersonDto currentPersonDto) {
		FinancialGoal financialGoal = dtoToModel(financialGoalDto);
		Person person = PersonMapper.dtoToModel(currentPersonDto);

		log.info("Обновление долгосрочной финансовой цели с ID: '{}' пользователя с ID: '{}'",
				 financialGoal.getFinancialGoalId(),
				 person.getPersonId());

		FinancialGoal financialGoalUpdate = findById(
				financialGoal.getFinancialGoalId(),
				person.getPersonId()).orElseThrow(() -> {
					log.warn("Не удалось обновить долгосрочную финансовую цель с ID '{}' - долгосрочная финансовая цель не найдена",
							 financialGoal.getFinancialGoalId());
			return new IllegalArgumentException(NOT_FOUND_FINANCIAL_GOAL_MESSAGE);
		});

		log.info("Обновление данных долгосрочной финансовой цели с ID: '{}' пользователя с ID: '{}'",
				 financialGoalUpdate.getFinancialGoalId(),
				 person.getPersonId());

		String name = validateName(financialGoal.getName());
		BigDecimal targetAmount = validateTargetAmount(financialGoal.getTargetAmount());
		LocalDate endDate = validateEndDate(financialGoal.getEndDate());

		financialGoalUpdate.setName(name);
		financialGoalUpdate.setTargetAmount(targetAmount);
		financialGoalUpdate.setEndDate(endDate);

		log.info("Создана подготовленная модель обновляемой долгосрочной финансовой цели с ID: '{}'",
				 financialGoalUpdate.getFinancialGoalId());
		try {
			financialGoalRepository.update(financialGoalUpdate);
			log.info("Долгосрочная финансовая цель с ID '{}' успешно обновлена",
					 financialGoalUpdate.getFinancialGoalId());
			return Optional.of(modelToDto(financialGoalUpdate));
		} catch (RuntimeException e) {
			log.error("Ошибка при обновлении долгосрочной финансовой цели по ID '{}': '{}'",
					  financialGoalUpdate.getFinancialGoalId(),
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<FinancialGoalDto> getAllSortByEndDate(LocalDate endDate, PersonDto currentPersonDto) {
		return List.of();
	}

	@Override
	public void delete(FinancialGoalDto financialGoalDto, PersonDto currentPersonDto) {
		FinancialGoal financialGoal = dtoToModel(financialGoalDto);
		Person person = dtoToModel(currentPersonDto);

		log.info("Удаление долгосрочной финансовой цели с ID: '{}' пользователя с ID: '{}'",
				 financialGoal.getFinancialGoalId(),
				 person.getPersonId());

		FinancialGoal financialGoalToDelete = findById(
				financialGoal.getFinancialGoalId(),
				person.getPersonId()).orElseThrow(() -> {
					log.warn("Не удалось удалить долгосрочную финансовую цель с ID '{}' - долгосрочная финансовая цель не найдена",
							 financialGoal.getFinancialGoalId());
			return new IllegalArgumentException(NOT_FOUND_FINANCIAL_GOAL_MESSAGE);
		});

		try {
			financialGoalRepository.delete(financialGoalToDelete.getFinancialGoalId(), person.getPersonId());
			log.info("Данные долгосрочной финансовой цели с ID '{}' удалены",
					 financialGoalToDelete.getFinancialGoalId());
		} catch (RuntimeException e) {
			log.error("Ошибка при удалении данных долгосрочной финансовой цели с ID '{}': '{}'",
					  financialGoalToDelete.getFinancialGoalId(),
					  e.getMessage(), e);
			throw e;
		}
	}
}