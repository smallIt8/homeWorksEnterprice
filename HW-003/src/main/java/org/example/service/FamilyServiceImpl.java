package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.*;
import org.example.model.*;
import org.example.repository.FamilyRepository;

import java.util.*;

import static org.example.mapper.PersonMapper.*;
import static org.example.mapper.FamilyMapper.*;
import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.InfoMessageConstant.*;
import static org.example.util.constant.RegexConstant.*;

@Slf4j
@RequiredArgsConstructor
public class FamilyServiceImpl implements FamilyService {

	private final FamilyRepository familyRepository;

	@Override
	public void create(FamilyDto FamilyDto) {
		Family family = dtoToModel(FamilyDto);

		log.info("Создание новой семейной группы пользователя с ID: '{}'",
				 family.getCreator().getPersonId());

		Family inputFamily = buildValidatedFamily(family);

		log.info("Создана подготовленная модель создаваемой семейной группы '{}' пользователя: '{}'",
				 inputFamily.getName(),
				 inputFamily.getCreator().getPersonId());

		try {
			familyRepository.create(inputFamily);
			log.info("Семейная группа с ID: '{}' успешно создана для пользователя с ID: '{}'",
					 inputFamily.getFamilyId(),
					 inputFamily.getCreator().getPersonId());
		} catch (RuntimeException e) {
			log.error("Ошибка при создании семейной группы: '{}'",
					  e.getMessage(), e);
			throw e;
		}
	}

	private String validateName(String name) {
		log.info("Создание имени семейной группы");
		if (name.matches(FAMILY_NAME_REGEX)) {
			log.info("Имя семейной группы успешно создано: '{}'",
					 name);
			return name;
		} else {
			log.error("Неверный формат имени: '{}'",
					  name);
			throw new IllegalArgumentException(ERROR_ENTER_FAMILY_NAME_MESSAGE);
		}
	}

	@Override
	public Map<FamilyDto, String> createBatch(List<FamilyDto> familiesDto) {
		List<Family> families = dtoToModelList(familiesDto);

		log.info("Добавление пакета семейных групп: количество = '{}'",
				 familiesDto.size());

		Map<FamilyDto, String> errors = new LinkedHashMap<>();
		List<Family> familiesSuccess = new ArrayList<>();

		for (int i = 0; i < families.size(); i++) {
			Family family = families.get(i);
			FamilyDto familyDto = familiesDto.get(i);
			try {
				Family validatedFamily = buildValidatedFamily(family);

				familiesSuccess.add(validatedFamily);
				log.info("Семейная группа с ID: '{}' успешно подготовлена для добавления",
						 validatedFamily.getFamilyId());
			} catch (RuntimeException e) {
				log.warn("Ошибка при создании семейной группы '{}' в пакете: '{}'",
						 family.getName(),
						 e.getMessage());
				errors.put(familyDto, e.getMessage());
			}
		}
		if (!familiesSuccess.isEmpty()) {
			familyRepository.createBatch(familiesSuccess);
			log.info("Успешно добавленные семейные группы: '{}'",
					 familiesSuccess.size());
		}
		log.info("Пакет семейных групп обработан, успешно: '{}',  количество ошибок: '{}'",
				 familiesSuccess.size(),
				 errors.size());
		return errors;
	}

	private Family buildValidatedFamily(Family family) {
		String name = validateName(family.getName());

		return Family.builder()
				.familyId(UUID.randomUUID())
				.name(name.toUpperCase())
				.creator(family.getCreator())
				.build();
	}

	@Override
	public boolean addMember(String email, PersonDto ownerPersonDto) {
		return false;
	}

	@Override
	public Optional<Family> findById(UUID familyId, UUID currentPersonId) {
		log.info("Получение данных семейной группы по ID: '{}'",
				 familyId);
		try {
			Optional<Family> familyOpt = familyRepository.findById(familyId);
			if (familyOpt.isEmpty()) {
				log.warn("Семейная группа с ID: '{}' не найдена",
						 familyId);
				throw new IllegalArgumentException(NOT_FOUND_FAMILY_MESSAGE);
			}
			if (!familyOpt.get().getCreator().getPersonId().equals(currentPersonId)) {
				log.warn("Семейная группа с ID: '{}' не принадлежит пользователю с ID: '{}'",
						 familyId,
						 currentPersonId);
				throw new SecurityException(ERROR_ACCESS_FAMILY_MESSAGE);
			}
			return familyOpt;
		} catch (RuntimeException e) {
			log.error("Ошибка при получении данных семейной группы по ID '{}': '{}'",
					  familyId,
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<FamilyDto> findAll(PersonDto currentPersonDto) {
		log.info("Получение списка семейных групп в которые вступил пользователь с ID: '{}'",
				 currentPersonDto.getPersonId());
		try {
			List<Family> families = familyRepository.findAll(currentPersonDto.getPersonId());
			if (families.isEmpty()) {
				log.warn("Пользователь с ID '{}' не состоит в семейных группах",
						 currentPersonDto.getPersonId());
				throw new IllegalArgumentException(EMPTY_LIST_FAMILY_BY_PERSON_MESSAGE);
			}
				log.info("Получено '{}' семейных групп в которые вступил пользователь с ID '{}':",
						 families.size(),
						 currentPersonDto.getPersonId());
			return modelToDtoList(families);
		} catch (RuntimeException e) {
			log.error("Ошибка при получении списка семейных групп в которые вступил пользователь с ID '{}': '{}'",
					  currentPersonDto.getPersonId(),
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<FamilyDto> findAllOwnerFamily(PersonDto currentPersonDto) {
		log.info("Получение списка семейных групп пользователя с ID: '{}'",
				 currentPersonDto.getPersonId());
		try {
			List<Family> families = familyRepository.findAllByOwner(currentPersonDto.getPersonId());
			if (families.isEmpty()) {
				log.warn("Список семейных групп пользователя с ID '{}' пуст",
						 currentPersonDto.getPersonId());
				throw new IllegalArgumentException(EMPTY_LIST_FAMILY_BY_OWNER_PERSON_MESSAGE);
			}
			log.info("Получено '{}' семейных групп пользователя с ID '{}':",
					 families.size(),
					 currentPersonDto.getPersonId());
			return modelToDtoList(families);
		} catch (RuntimeException e) {
			log.error("Ошибка при получении списка семейных групп пользователя с ID '{}': '{}'",
					  currentPersonDto.getPersonId(),
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Optional<FamilyDto> update(FamilyDto familyDto, PersonDto currentPersonDto) {
		Family family = dtoToModel(familyDto);
		Person person = dtoToModel(currentPersonDto);

		log.info("Обновление семейной группы с ID: '{}' пользователя с ID: '{}'",
				 family.getFamilyId(),
				 person.getPersonId());

		Family familyUpdate = findById(
				family.getFamilyId(),
				person.getPersonId()).orElseThrow(() -> {
			log.warn("Не удалось обновить семейную группу с ID '{}' - семейная группа не найдена",
					 family.getFamilyId());
			return new IllegalArgumentException(NOT_FOUND_FAMILY_MESSAGE);
		});

		log.info("Обновление данных семейной группы с ID: '{}' пользователя с ID: '{}'",
				 familyUpdate.getFamilyId(),
				 person.getPersonId());

		String name = validateName(family.getName());

		familyUpdate.setName(name.toUpperCase());

		log.info("Создана подготовленная модель обновляемой семейной группы с ID: '{}'",
				 familyUpdate.getFamilyId());
		try {
			familyRepository.update(familyUpdate);
			log.info("Семейная группа с ID '{}' успешно обновлена",
					 familyUpdate.getFamilyId());
			return Optional.of(modelToDto(familyUpdate));
		} catch (RuntimeException e) {
			log.error("Ошибка при обновлении семейной группы по ID '{}': '{}'",
					  familyUpdate.getFamilyId(),
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public boolean exitFamily(PersonDto currentPersonDto) {
		return false;
	}

	@Override
	public void delete(FamilyDto familyDto, PersonDto currentPersonDto) {
		Family family = dtoToModelLight(familyDto);
		Person person = dtoToModel(currentPersonDto);

		log.info("Удаление семейной группы с ID: '{}' пользователя с ID: '{}'",
				 family.getFamilyId(),
				 person.getPersonId());

		Family familyToDelete = findById(
				family.getFamilyId(),
				person.getPersonId()).orElseThrow(() -> {
			log.warn("Не удалось удалить семейную группу с ID '{}' - семейная группа не найдена",
					 family.getFamilyId());
			return new IllegalArgumentException(NOT_FOUND_FAMILY_MESSAGE);
		});

		try {
			familyRepository.delete(familyToDelete.getFamilyId(), person.getPersonId());
			log.info("Данные семейной группы с ID '{}' удалены",
					 familyToDelete.getFamilyId());
		} catch (RuntimeException e) {
			log.error("Ошибка при удалении данных семейной группы с ID '{}': '{}'",
					  familyToDelete.getFamilyId(),
					  e.getMessage(), e);
			throw e;
		}
	}
}