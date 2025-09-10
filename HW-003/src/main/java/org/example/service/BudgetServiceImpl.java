package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.*;
import org.example.model.*;
import org.example.repository.BudgetRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import static org.example.mapper.PersonMapper.*;
import static org.example.mapper.BudgetMapper.*;
import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.InfoMessageConstant.*;
import static org.example.util.constant.RegexConstant.*;

@Slf4j
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

	private final BudgetRepository budgetRepository;

	@Override
	public void create(BudgetDto budgetDto) {
		Budget budget = dtoToModel(budgetDto);

		log.info("Установление нового бюджета пользователя с ID: '{}'",
				 budget.getCreator().getPersonId());

		Budget inputBudget = buildValidatedBudget(budget);

		log.info("Создана подготовленная модель устанавливаемого бюджета '{}' пользователя: '{}' на категорию '{}'",
				 inputBudget.getName(),
				 inputBudget.getCreator().getPersonId(),
				 inputBudget.getCategory().getName());
		try {
			budgetRepository.create(inputBudget);
			log.info("Бюджет с ID: '{}' успешно установлен на категорию '{}' для пользователя c ID: '{}'",
					 inputBudget.getBudgetId(),
					 inputBudget.getCategory().getName(),
					 inputBudget.getCreator().getPersonId());
		} catch (RuntimeException e) {
			log.error("Ошибка при установке бюджета: '{}'",
					  e.getMessage(), e);
			throw e;
		}
	}

	private String validateName(String name) {
		log.info("Создание имени бюджета");
		if (name.matches(BUDGET_NAME_REGEX)) {
			log.info("Имя бюджета успешно создано: '{}'",
					 name);
			return name;
		} else {
			log.error("Неверный формат имени: '{}'",
					  name);
			throw new IllegalArgumentException(ERROR_ENTER_BUDGET_NAME_MESSAGE);
		}
	}

	private Category validateCategory(Category category) {
		log.info("Проверка категории бюджета");
		if (category == null) {
			log.error("Категория в бюджете не может быть пустой");
			throw new IllegalArgumentException(ERROR_SELECT_BUDGET_CATEGORY_MESSAGE);
		}
		// Проверить истинный тип категории - не может быть расходной
		log.info("Категория бюджета успешно проверена: '{}'",
				 category);
		return category;
	}

	private BigDecimal validateLimit(BigDecimal limit) {
		log.info("Проверка устанавливаемого лимита бюджета");
		if (limit == null || limit.compareTo(BigDecimal.ZERO) <= 0) {
			log.error("Устанавливаемый лимит бюджета '{}' не может быть пустым, 0 или быть меньше 0",
					  limit);
			throw new IllegalArgumentException(ERROR_CREATION_BUDGET_LIMIT_MESSAGE);
		}
		log.info("Устанавливаемый лимит бюджета успешно проверен: '{}'",
				 limit);
		return limit;
	}

	private YearMonth validatePeriod(YearMonth period) {
		log.info("Проверка устанавливаемого периода бюджета");
		if (period == null || period.isBefore(YearMonth.now())) {
			log.error("Устанавливаемый период бюджета '{}' не может быть пустым и не может быть раньше текущего месяца",
					  period);
			throw new IllegalArgumentException(ERROR_CREATION_BUDGET_PERIOD_MESSAGE);
		}
		log.info("Устанавливаемый период бюджета успешно проверен: '{}'",
				 period);
		return period;
	}

	@Override
	public Map<BudgetDto, String> createBatch(List<BudgetDto> budgetsDto) {
		List<Budget> budgets = dtoToModelList(budgetsDto);

		log.info("Добавление пакета бюджетов: количество = '{}'",
				 budgetsDto.size());

		Map<BudgetDto, String> errors = new LinkedHashMap<>();
		List<Budget> budgetsSuccess = new ArrayList<>();

		for (int i = 0; i < budgets.size(); i++) {
			Budget budget = budgets.get(i);
			BudgetDto budgetDto = budgetsDto.get(i);
			try {
				Budget validatedBudget = buildValidatedBudget(budget);

				budgetsSuccess.add(validatedBudget);
				log.info("Бюджет с ID: '{}' успешно подготовлен для добавления",
						 validatedBudget.getBudgetId());
			} catch (RuntimeException e) {
				log.warn("Ошибка при создании бюджета '{}' в пакете: '{}'",
						 budget.getName(),
						 e.getMessage());
				errors.put(budgetDto, e.getMessage());
			}
		}
		if (!budgetsSuccess.isEmpty()) {
			budgetRepository.createBatch(budgetsSuccess);
			log.info("Успешно добавленные бюджеты: '{}'",
					 budgetsSuccess.size());
		}
		log.info("Пакет бюджетов обработан, успешно: '{}',  количество ошибок: '{}'",
				 budgetsSuccess.size(),
				 errors.size());
		return errors;
	}

	private Budget buildValidatedBudget(Budget budget) {
		String name = validateName(budget.getName());
		Category category = validateCategory(budget.getCategory());
		BigDecimal limit = validateLimit(budget.getLimit());
		YearMonth period = validatePeriod(budget.getPeriod());

		return Budget.builder()
				.budgetId(UUID.randomUUID())
				.name(name.toLowerCase())
				.category(category)
				.limit(limit)
				.period(period)
				.creator(budget.getCreator())
				.build();
	}

	@Override
	public Optional<Budget> findById(UUID budgetId, UUID currentPersonId) {
		log.info("Получение данных бюджета по ID: '{}'",
				 budgetId);
		try {
			Optional<Budget> budgetOpt = budgetRepository.findById(budgetId);
			if (budgetOpt.isEmpty()) {
				log.warn("Бюджет с ID: '{}' не найден",
						 budgetId);
				throw new IllegalArgumentException(NOT_FOUND_BUDGET_MESSAGE);
			}
			if (!budgetOpt.get().getCreator().getPersonId().equals(currentPersonId)) {
				log.warn("Бюджет с ID: '{}' не принадлежит пользователю с ID: '{}'",
						 budgetId,
						 currentPersonId);
				throw new SecurityException(ERROR_ACCESS_BUDGET_MESSAGE);
			}
			return budgetOpt;
		} catch (RuntimeException e) {
			log.error("Ошибка при получении данных бюджета по ID '{}': '{}'",
					  budgetId,
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<BudgetDto> findAll(PersonDto currentPersonDto) {
		log.info("Получение списка бюджетов пользователя с ID: '{}'",
				 currentPersonDto.getPersonId());
		try {
			List<Budget> budgets = budgetRepository.findAll(currentPersonDto.getPersonId());
			if (budgets.isEmpty()) {
				log.info("Список бюджетов пользователя с ID '{}' пуст",
						 currentPersonDto.getPersonId());
				throw new IllegalArgumentException(EMPTY_LIST_BUDGET_BY_PERSON_MESSAGE);
			}
			log.info("Получено '{}' бюджетов пользователя с ID '{}':",
					 budgets.size(),
					 currentPersonDto.getPersonId());
			return modelToDtoList(budgets);
		} catch (RuntimeException e) {
			log.error("Ошибка при получении списка бюджетов пользователя '{}': '{}'",
					  currentPersonDto.getPersonId(),
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Optional<BudgetDto> update(BudgetDto budgetDto, PersonDto currentPersonDto) {
		Budget budget = dtoToModel(budgetDto);
		Person person = dtoToModel(currentPersonDto);

		log.info("Обновление бюджета с ID: '{}' пользователя с ID: '{}'",
				 budget.getBudgetId(),
				 person.getPersonId());

		Budget budgetUpdate = findById(
				budget.getBudgetId(),
				person.getPersonId()).orElseThrow(() -> {
					log.warn("Не удалось обновить бюджет с ID '{}' - бюджет не найдена",
							 budget.getBudgetId());
					return new IllegalArgumentException(NOT_FOUND_BUDGET_MESSAGE);
		});

		log.info("Обновление данных бюджета с ID: '{}' пользователя с ID: '{}'",
				 budgetUpdate.getBudgetId(),
				 person.getPersonId());

		String name = validateName(budget.getName());
		Category category = validateCategory(budget.getCategory());
		BigDecimal limit = validateLimit(budget.getLimit());
		YearMonth period = validatePeriod(budget.getPeriod());

		budgetUpdate.setName(name.toLowerCase());
		budgetUpdate.setCategory(category);
		budgetUpdate.setLimit(limit);
		budgetUpdate.setPeriod(period);

		log.info("Создана подготовленная модель обновляемого бюджета с ID: '{}'",
				 budgetUpdate.getBudgetId());
		try {
			budgetRepository.update(budgetUpdate);
			log.info("Бюджет с ID '{}' успешно обновлен",
					 budgetUpdate.getBudgetId());
			return Optional.of(modelToDto(budgetUpdate));
		} catch (RuntimeException e) {
			log.error("Ошибка при обновлении бюджета по ID '{}': '{}'",
					  budgetUpdate.getBudgetId(),
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<BudgetDto> getAllSortByDate(LocalDate date, PersonDto currentPersonDto) {
		return List.of();
	}

	@Override
	public List<BudgetDto> getAllSortByCategory(CategoryDto categoryDto, PersonDto currentPersonDto) {
		return List.of();
	}

	@Override
	public List<BudgetDto> getAllSortByCategoryAndDate(CategoryDto categoryDto, LocalDate date, PersonDto currentPersonDto) {
		return List.of();
	}

	@Override
	public List<BudgetDto> getAllSortByCreateDate(LocalDate createDate, PersonDto currentPersonDto) {
		return List.of();
	}

	@Override
	public void delete(BudgetDto budgetDto, PersonDto currentPersonDto) {
		Budget budget = dtoToModelLight(budgetDto);
		Person person = dtoToModel(currentPersonDto);

		log.info("Удаление бюджета с ID: '{}' пользователя с ID: '{}'",
				 budget.getBudgetId(),
				 person.getPersonId());

		Budget budgetToDelete = findById(
				budget.getBudgetId(),
				person.getPersonId()).orElseThrow(() -> {
					log.warn("Не удалось удалить бюджет с ID '{}' - бюджет не найден",
							 budget.getBudgetId());
					return new IllegalArgumentException(NOT_FOUND_BUDGET_MESSAGE);
		});

		try {
			budgetRepository.delete(budgetToDelete.getBudgetId(), person.getPersonId());
			log.info("Данные бюджета с ID '{}' удалены",
					 budgetToDelete.getBudgetId());
		} catch (RuntimeException e) {
			log.error("Ошибка при удалении данных бюджета с ID '{}': '{}'",
					  budgetToDelete.getBudgetId(),
					  e.getMessage(), e);
			throw e;
		}
	}
}