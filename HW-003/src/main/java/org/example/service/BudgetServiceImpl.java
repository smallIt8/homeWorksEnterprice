package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BudgetDto;
import org.example.dto.PersonDto;
import org.example.mapper.BudgetMapper;
import org.example.mapper.PersonMapper;
import org.example.model.Budget;
import org.example.repository.BudgetRepository;
import org.example.validator.Validator;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.example.util.ValidationDtoUtil.validateAnnotation;
import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.InfoMessageConstant.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class BudgetServiceImpl implements BudgetService {

	private final BudgetRepository budgetRepository;
	private final BudgetMapper budgetMapper;
	private final PersonMapper personMapper;
	private final Validator<BudgetDto> validator;

	@Override
	public void create(BudgetDto budgetDto) {
		log.debug("Установление нового бюджета пользователя с ID: '{}'", budgetDto.getCreatorDto().getPersonId());
		var inputBudget = buildBudget(budgetDto);

		final var budget = budgetMapper.mapDtoToModel(inputBudget);
		log.debug("Создана подготовленная модель устанавливаемого бюджета '{}' пользователя: '{}' на категорию '{}'",
				 budget.getBudgetName(),
				 budget.getCreator().getPersonId(),
				 budget.getCategory().getCategoryName());
		try {
			budgetRepository.create(budget);
			log.info("Бюджет с ID: '{}' успешно установлен на категорию '{}' для пользователя c ID: '{}'",
					 budget.getBudgetId(),
					 budget.getCategory().getCategoryName(),
					 budget.getCreator().getPersonId());
		} catch (RuntimeException e) {
			log.error("Ошибка при установке бюджета: '{}'", e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Map<BudgetDto, String> createBatch(List<BudgetDto> budgetsDto) {
		log.debug("Добавление пакета бюджетов: количество = '{}'", budgetsDto.size());
		Map<BudgetDto, String> errors = new LinkedHashMap<>();
		List<Budget> budgetsSuccess = new ArrayList<>();

		for (var budgetDto : budgetsDto) {
			try {
				var validDto = buildBudget(budgetDto);
				budgetsSuccess.add(budgetMapper.mapDtoToModel(validDto));
				log.debug("Бюджет с ID: '{}' успешно подготовлен для добавления", validDto.getBudgetId());
			} catch (RuntimeException e) {
				log.error("Ошибка при создании бюджета '{}' в пакете: '{}'", budgetDto.getBudgetName(), e.getMessage());
				errors.put(budgetDto, e.getMessage());
			}
		}
		if (!budgetsSuccess.isEmpty()) {
			budgetRepository.createBatch(budgetsSuccess);
			log.info("Успешно добавленные бюджеты: '{}'", budgetsSuccess.size());
		}
		log.info("Пакет бюджетов обработан, успешно: '{}',  количество ошибок: '{}'", budgetsSuccess.size(), errors.size());
		return errors;
	}

	private BudgetDto buildBudget(BudgetDto budgetDto) {
		validateAnnotation(budgetDto);

		validator.validate(budgetDto);

		return BudgetDto.builder()
				.budgetId(UUID.randomUUID())
				.budgetName(budgetDto.getBudgetName())
				.categoryDto(budgetDto.getCategoryDto())
				.limit(budgetDto.getLimit())
				.period(budgetDto.getPeriod())
				.creatorDto(budgetDto.getCreatorDto())
				.build();
	}

	@Override
	public Optional<Budget> findById(UUID budgetId, UUID currentPersonId) {
		log.debug("Получение данных бюджета по ID: '{}'", budgetId);
		try {
			Optional<Budget> budgetOpt = budgetRepository.findById(budgetId);
			if (budgetOpt.isEmpty()) {
				log.warn("Бюджет с ID: '{}' не найден", budgetId);
				throw new IllegalArgumentException(NOT_FOUND_BUDGET_MESSAGE);
			}
			if (!budgetOpt.get().getCreator().getPersonId().equals(currentPersonId)) {
				log.warn("Бюджет с ID: '{}' не принадлежит пользователю с ID: '{}'", budgetId, currentPersonId);
				throw new SecurityException(ERROR_ACCESS_BUDGET_MESSAGE);
			}
			log.info("Данные бюджета с ID: '{}' успешно получены", budgetId);
			return budgetOpt;
		} catch (RuntimeException e) {
			log.error("Ошибка при получении данных бюджета по ID '{}': '{}'", budgetId, e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<BudgetDto> findAll(PersonDto currentPersonDto) {
		log.debug("Получение списка бюджетов пользователя с ID: '{}'", currentPersonDto.getPersonId());
		try {
			List<Budget> budgets = budgetRepository.findAll(currentPersonDto.getPersonId());
			if (budgets.isEmpty()) {
				log.warn("Список бюджетов пользователя с ID '{}' пуст", currentPersonDto.getPersonId());
				throw new IllegalArgumentException(EMPTY_LIST_BUDGET_BY_PERSON_MESSAGE);
			}
			log.info("Получено '{}' бюджетов пользователя с ID '{}':", budgets.size(), currentPersonDto.getPersonId());
			return budgetMapper.mapModelToDtoList(budgets);
		} catch (RuntimeException e) {
			log.error("Ошибка при получении списка бюджетов пользователя '{}': '{}'",
					  currentPersonDto.getPersonId(),
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Optional<BudgetDto> update(BudgetDto budgetDto, PersonDto currentPersonDto) {
		final var budget = budgetMapper.mapDtoToModel(budgetDto);
		final var person = personMapper.mapDtoToModel(currentPersonDto);

		log.debug("Обновление бюджета с ID: '{}' пользователя с ID: '{}'", budget.getBudgetId(), person.getPersonId());
		var budgetUpdate = findById(
				budget.getBudgetId(),
				person.getPersonId()).orElseThrow(() -> {
					log.warn("Не удалось обновить бюджет с ID '{}' - бюджет не найдена", budget.getBudgetId());
					return new IllegalArgumentException(NOT_FOUND_BUDGET_MESSAGE);
		});

		log.debug("Обновление данных бюджета с ID: '{}' пользователя с ID: '{}'",
				 budgetUpdate.getBudgetId(),
				 person.getPersonId());

		validateAnnotation(budgetDto);
		validator.validate(budgetDto);

		budgetUpdate.setBudgetName(budget.getBudgetName());
		budgetUpdate.setCategory(budget.getCategory());
		budgetUpdate.setLimit(budget.getLimit());
		budgetUpdate.setPeriod(budget.getPeriod());

		log.debug("Создана подготовленная модель обновляемого бюджета с ID: '{}'", budgetUpdate.getBudgetId());
		try {
			budgetRepository.update(budgetUpdate);
			log.info("Бюджет с ID '{}' успешно обновлен", budgetUpdate.getBudgetId());
			return Optional.of(budgetMapper.mapModelToDto(budgetUpdate));
		} catch (RuntimeException e) {
			log.error("Ошибка при обновлении бюджета по ID '{}': '{}'", budgetUpdate.getBudgetId(), e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void delete(BudgetDto budgetDto, PersonDto currentPersonDto) {
		final var budget = budgetMapper.mapDtoToModelLight(budgetDto);
		final var person = personMapper.mapDtoToModel(currentPersonDto);

		log.debug("Удаление бюджета с ID: '{}' пользователя с ID: '{}'", budget.getBudgetId(), person.getPersonId());
		var budgetToDelete = findById(
				budget.getBudgetId(),
				person.getPersonId()).orElseThrow(() -> {
					log.warn("Не удалось удалить бюджет с ID '{}' - бюджет не найден", budget.getBudgetId());
					return new IllegalArgumentException(NOT_FOUND_BUDGET_MESSAGE);
		});

		try {
			budgetRepository.delete(budgetToDelete.getBudgetId(), person.getPersonId());
			log.info("Данные бюджета с ID '{}' удалены", budgetToDelete.getBudgetId());
		} catch (RuntimeException e) {
			log.error("Ошибка при удалении данных бюджета с ID '{}': '{}'", budgetToDelete.getBudgetId(), e.getMessage(), e);
			throw e;
		}
	}
}
