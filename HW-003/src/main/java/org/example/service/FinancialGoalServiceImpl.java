package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.FinancialGoalDto;
import org.example.dto.PersonDto;
import org.example.mapper.FinancialGoalMapper;
import org.example.mapper.PersonMapper;
import org.example.model.FinancialGoal;
import org.example.model.Person;
import org.example.repository.FinancialGoalRepository;

import java.util.*;

import static org.example.util.ValidationDtoUtil.validateAnnotation;
import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.InfoMessageConstant.*;

@Slf4j
@RequiredArgsConstructor
public class FinancialGoalServiceImpl implements FinancialGoalService {

	private final FinancialGoalRepository financialGoalRepository;
	private final FinancialGoalMapper financialGoalMapper;
	private final PersonMapper personMapper;

	@Override
	public void create(FinancialGoalDto financialGoalDto) {
		log.info("Добавление новой долгосрочной финансовой цели пользователя с ID: '{}'", financialGoalDto.getCreatorDto().getPersonId());
		FinancialGoalDto inputFinancialGoal = buildFinancialGoal(financialGoalDto);

		FinancialGoal financialGoal = financialGoalMapper.mapDtoToModel(inputFinancialGoal);
		log.info("Создана подготовленная модель добавляемой долгосрочной финансовой цели '{}' пользователя: '{}'",
				 financialGoal.getFinancialGoalName(),
				 financialGoal.getCreator().getPersonId());
		try {
			financialGoalRepository.create(financialGoal);
			log.info("Долгосрочная финансовая цель с ID: '{}' успешно создана для пользователя c ID: '{}'",
					 financialGoal.getFinancialGoalId(),
					 financialGoal.getCreator().getPersonId());
		} catch (RuntimeException e) {
			log.error("Ошибка при создании долгосрочной финансовой цели: '{}'",
					  e.getMessage(),
					  e);
			throw e;
		}
	}

	@Override
	public Map<FinancialGoalDto, String> createBatch(List<FinancialGoalDto> financialGoalsDto) {
		log.info("Добавление пакета долгосрочных финансовых целей: количество = '{}'", financialGoalsDto.size());
		Map<FinancialGoalDto, String> errors = new LinkedHashMap<>();
		List<FinancialGoal> financialGoalsSuccess = new ArrayList<>();

		for (FinancialGoalDto financialGoalDto : financialGoalsDto) {
			try {
				FinancialGoalDto validFinancialGoal = buildFinancialGoal(financialGoalDto);
				financialGoalsSuccess.add(financialGoalMapper.mapDtoToModel(validFinancialGoal));
				log.info("Долгосрочная финансовая цель с ID: '{}' успешно подготовлена для добавления", validFinancialGoal.getFinancialGoalId());
			} catch (RuntimeException e) {
				log.warn("Ошибка при создании долгосрочной финансовой цели '{}' в пакете: '{}'",
						 financialGoalDto.getFinancialGoalName(),
						 e.getMessage());
				errors.put(financialGoalDto, e.getMessage());
			}
		}
		if (!financialGoalsSuccess.isEmpty()) {
			financialGoalRepository.createBatch(financialGoalsSuccess);
			log.info("Успешно добавленные долгосрочные финансовые цели: '{}'", financialGoalsSuccess.size());
		}
		log.info("Пакет долгосрочных финансовых целей обработан, успешно: '{}',  количество ошибок: '{}'",
				 financialGoalsSuccess.size(),
				 errors.size());
		return errors;
	}

	private FinancialGoalDto buildFinancialGoal(FinancialGoalDto financialGoalDto) {
		validateAnnotation(financialGoalDto);

		return FinancialGoalDto.builder()
				.financialGoalId(UUID.randomUUID())
				.financialGoalName(financialGoalDto.getFinancialGoalName())
				.targetAmount(financialGoalDto.getTargetAmount())
				.endDate(financialGoalDto.getEndDate())
				.creatorDto(financialGoalDto.getCreatorDto())
				.build();
	}

	@Override
	public Optional<FinancialGoal> findById(UUID financialGoalId, UUID currentPersonId) {
		log.info("Получение данных долгосрочной финансовой цели по ID: '{}'", financialGoalId);
		try {
			Optional<FinancialGoal> financialGoalOpt = financialGoalRepository.findById(financialGoalId);
			if (financialGoalOpt.isEmpty()) {
				log.warn("Долгосрочная финансовая цель с ID: '{}' не найдена", financialGoalId);
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
					  e.getMessage(),
					  e);
			throw e;
		}
	}

	@Override
	public List<FinancialGoalDto> findAll(PersonDto currentPersonDto) {
		log.info("Получение списка долгосрочных финансовых целей пользователя с ID: '{}'", currentPersonDto.getPersonId());
		try {
			List<FinancialGoal> financialGoals = financialGoalRepository.findAll(currentPersonDto.getPersonId());
			if (financialGoals.isEmpty()) {
				log.info("Список долгосрочных финансовых целей пользователя с ID '{}' пуст", currentPersonDto.getPersonId());
				throw new IllegalArgumentException(EMPTY_LIST_FINANCIAL_GOAL_BY_PERSON_MESSAGE);
			}
			log.info("Получено '{}' долгосрочных финансовых целей пользователя с ID '{}':",
					 financialGoals.size(),
					 currentPersonDto.getPersonId());
			return financialGoalMapper.mapModelToDtoList(financialGoals);
		} catch (RuntimeException e) {
			log.error("Ошибка при получении списка долгосрочных финансовых целей пользователя '{}': '{}'",
					  currentPersonDto.getPersonId(),
					  e.getMessage(),
					  e);
			throw e;
		}
	}

	@Override
	public Optional<FinancialGoalDto> update(FinancialGoalDto financialGoalDto, PersonDto currentPersonDto) {
		FinancialGoal financialGoal = financialGoalMapper.mapDtoToModel(financialGoalDto);
		Person person = personMapper.mapDtoToModel(currentPersonDto);

		log.info("Обновление долгосрочной финансовой цели с ID: '{}' пользователя с ID: '{}'",
				 financialGoal.getFinancialGoalId(),
				 person.getPersonId());

		FinancialGoal financialGoalUpdate = findById(
				financialGoal.getFinancialGoalId(),
				person.getPersonId()).orElseThrow(() -> {
			log.warn("Не удалось обновить долгосрочную финансовую цель с ID '{}' - долгосрочная финансовая цель не найдена", financialGoal.getFinancialGoalId());
			return new IllegalArgumentException(NOT_FOUND_FINANCIAL_GOAL_MESSAGE);
		});

		log.info("Обновление данных долгосрочной финансовой цели с ID: '{}' пользователя с ID: '{}'",
				 financialGoalUpdate.getFinancialGoalId(),
				 person.getPersonId());

		validateAnnotation(financialGoalDto);

		financialGoalUpdate.setFinancialGoalName(financialGoal.getFinancialGoalName());
		financialGoalUpdate.setTargetAmount(financialGoal.getTargetAmount());
		financialGoalUpdate.setEndDate(financialGoal.getEndDate());

		log.info("Создана подготовленная модель обновляемой долгосрочной финансовой цели с ID: '{}'", financialGoalUpdate.getFinancialGoalId());
		try {
			financialGoalRepository.update(financialGoalUpdate);
			log.info("Долгосрочная финансовая цель с ID '{}' успешно обновлена", financialGoalUpdate.getFinancialGoalId());
			return Optional.of(financialGoalMapper.mapModelToDto(financialGoalUpdate));
		} catch (RuntimeException e) {
			log.error("Ошибка при обновлении долгосрочной финансовой цели по ID '{}': '{}'",
					  financialGoalUpdate.getFinancialGoalId(),
					  e.getMessage(),
					  e);
			throw e;
		}
	}

	@Override
	public void delete(FinancialGoalDto financialGoalDto, PersonDto currentPersonDto) {
		FinancialGoal financialGoal = financialGoalMapper.mapDtoToModelLight(financialGoalDto);
		Person person = personMapper.mapDtoToModel(currentPersonDto);

		log.info("Удаление долгосрочной финансовой цели с ID: '{}' пользователя с ID: '{}'",
				 financialGoal.getFinancialGoalId(),
				 person.getPersonId());

		FinancialGoal financialGoalToDelete = findById(
				financialGoal.getFinancialGoalId(),
				person.getPersonId()).orElseThrow(() -> {
			log.warn("Не удалось удалить долгосрочную финансовую цель с ID '{}' - долгосрочная финансовая цель не найдена", financialGoal.getFinancialGoalId());
			return new IllegalArgumentException(NOT_FOUND_FINANCIAL_GOAL_MESSAGE);
		});

		try {
			financialGoalRepository.delete(financialGoalToDelete.getFinancialGoalId(), person.getPersonId());
			log.info("Данные долгосрочной финансовой цели с ID '{}' удалены", financialGoalToDelete.getFinancialGoalId());
		} catch (RuntimeException e) {
			log.error("Ошибка при удалении данных долгосрочной финансовой цели с ID '{}': '{}'",
					  financialGoalToDelete.getFinancialGoalId(),
					  e.getMessage(),
					  e);
			throw e;
		}
	}
}
