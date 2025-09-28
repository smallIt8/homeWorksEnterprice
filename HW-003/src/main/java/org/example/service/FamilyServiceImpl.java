package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.FamilyDto;
import org.example.dto.PersonDto;
import org.example.mapper.FamilyMapper;
import org.example.mapper.PersonMapper;
import org.example.model.Family;
import org.example.model.Person;
import org.example.repository.FamilyRepository;

import java.util.*;

import static org.example.util.ValidationDtoUtil.validateAnnotation;
import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.InfoMessageConstant.*;

@Slf4j
@RequiredArgsConstructor
public class FamilyServiceImpl implements FamilyService {

	private final FamilyRepository familyRepository;
	private final FamilyMapper familyMapper;
	private final PersonMapper personMapper;

	@Override
	public void create(FamilyDto familyDto) {
		log.info("Создание новой семейной группы пользователя с ID: '{}'", familyDto.getCreatorDto().getPersonId());
		FamilyDto inputFamily = buildFamily(familyDto);

		Family family = familyMapper.mapDtoToModel(inputFamily);
		log.info("Создана подготовленная модель создаваемой семейной группы '{}' пользователя: '{}'",
				 family.getFamilyName(),
				 family.getCreator().getPersonId());

		try {
			familyRepository.create(family);
			log.info("Семейная группа с ID: '{}' успешно создана для пользователя с ID: '{}'",
					 family.getFamilyId(),
					 family.getCreator().getPersonId());
		} catch (RuntimeException e) {
			log.error("Ошибка при создании семейной группы: '{}'",
					  e.getMessage(),
					  e);
			throw e;
		}
	}

	@Override
	public Map<FamilyDto, String> createBatch(List<FamilyDto> familiesDto) {
		log.info("Добавление пакета семейных групп: количество = '{}'", familiesDto.size());
		Map<FamilyDto, String> errors = new LinkedHashMap<>();
		List<Family> familiesSuccess = new ArrayList<>();

		for (FamilyDto familyDto : familiesDto) {
			try {
				FamilyDto validDto = buildFamily(familyDto);
				familiesSuccess.add(familyMapper.mapDtoToModel(validDto));
				log.info("Семейная группа с ID: '{}' успешно подготовлена для добавления", validDto.getFamilyId());
			} catch (RuntimeException e) {
				log.warn("Ошибка при создании семейной группы '{}' в пакете: '{}'",
						 familyDto.getFamilyName(),
						 e.getMessage());
				errors.put(familyDto, e.getMessage());
			}
		}
		if (!familiesSuccess.isEmpty()) {
			familyRepository.createBatch(familiesSuccess);
			log.info("Успешно добавленные семейные группы: '{}'", familiesSuccess.size());
		}
		log.info("Пакет семейных групп обработан, успешно: '{}',  количество ошибок: '{}'",
				 familiesSuccess.size(),
				 errors.size());
		return errors;
	}

	private FamilyDto buildFamily(FamilyDto familyDto) {
		validateAnnotation(familyDto);

		return FamilyDto.builder()
				.familyId(UUID.randomUUID())
				.familyName(familyDto.getFamilyName())
				.creatorDto(familyDto.getCreatorDto())
				.build();
	}

	//TODO: Будет реализовано в следующем ДЗ
	@Override
	public boolean addMember(String email, PersonDto ownerPersonDto) {
		return false;
	}

	@Override
	public Optional<Family> findById(UUID familyId, UUID currentPersonId) {
		log.info("Получение данных семейной группы по ID: '{}'", familyId);
		try {
			Optional<Family> familyOpt = familyRepository.findById(familyId);
			if (familyOpt.isEmpty()) {
				log.warn("Семейная группа с ID: '{}' не найдена", familyId);
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
					  e.getMessage(),
					  e);
			throw e;
		}
	}

	@Override
	public List<FamilyDto> findAll(PersonDto currentPersonDto) {
		log.info("Получение списка семейных групп в которые вступил пользователь с ID: '{}'", currentPersonDto.getPersonId());
		try {
			List<Family> families = familyRepository.findAll(currentPersonDto.getPersonId());
			if (families.isEmpty()) {
				log.warn("Пользователь с ID '{}' не состоит в семейных группах", currentPersonDto.getPersonId());
				throw new IllegalArgumentException(EMPTY_LIST_FAMILY_BY_PERSON_MESSAGE);
			}
			log.info("Получено '{}' семейных групп в которые вступил пользователь с ID '{}':",
					 families.size(),
					 currentPersonDto.getPersonId());
			return familyMapper.mapModelToDtoList(families);
		} catch (RuntimeException e) {
			log.error("Ошибка при получении списка семейных групп в которые вступил пользователь с ID '{}': '{}'",
					  currentPersonDto.getPersonId(),
					  e.getMessage(),
					  e);
			throw e;
		}
	}

	@Override
	public List<FamilyDto> findAllOwnerFamily(PersonDto currentPersonDto) {
		log.info("Получение списка семейных групп пользователя с ID: '{}'", currentPersonDto.getPersonId());
		try {
			List<Family> families = familyRepository.findAllByOwner(currentPersonDto.getPersonId());
			if (families.isEmpty()) {
				log.warn("Список семейных групп пользователя с ID '{}' пуст", currentPersonDto.getPersonId());
				throw new IllegalArgumentException(EMPTY_LIST_FAMILY_BY_OWNER_PERSON_MESSAGE);
			}
			log.info("Получено '{}' семейных групп пользователя с ID '{}':",
					 families.size(),
					 currentPersonDto.getPersonId());
			return familyMapper.mapModelToDtoList(families);
		} catch (RuntimeException e) {
			log.error("Ошибка при получении списка семейных групп пользователя с ID '{}': '{}'",
					  currentPersonDto.getPersonId(),
					  e.getMessage(),
					  e);
			throw e;
		}
	}

	@Override
	public Optional<FamilyDto> update(FamilyDto familyDto, PersonDto currentPersonDto) {
		Family family = familyMapper.mapDtoToModel(familyDto);
		Person person = personMapper.mapDtoToModel(currentPersonDto);

		log.info("Обновление семейной группы с ID: '{}' пользователя с ID: '{}'",
				 family.getFamilyId(),
				 person.getPersonId());

		Family familyUpdate = findById(
				family.getFamilyId(),
				person.getPersonId()).orElseThrow(() -> {
			log.warn("Не удалось обновить семейную группу с ID '{}' - семейная группа не найдена", family.getFamilyId());
			return new IllegalArgumentException(NOT_FOUND_FAMILY_MESSAGE);
		});

		log.info("Обновление данных семейной группы с ID: '{}' пользователя с ID: '{}'",
				 familyUpdate.getFamilyId(),
				 person.getPersonId());

		validateAnnotation(familyDto);

		familyUpdate.setFamilyName(familyDto.getFamilyName());

		log.info("Создана подготовленная модель обновляемой семейной группы с ID: '{}'", familyUpdate.getFamilyId());
		try {
			familyRepository.update(familyUpdate);
			log.info("Семейная группа с ID '{}' успешно обновлена", familyUpdate.getFamilyId());
			return Optional.of(familyMapper.mapModelToDto(familyUpdate));
		} catch (RuntimeException e) {
			log.error("Ошибка при обновлении семейной группы по ID '{}': '{}'",
					  familyUpdate.getFamilyId(),
					  e.getMessage(),
					  e);
			throw e;
		}
	}

	//TODO: Будет реализовано в следующем ДЗ
	@Override
	public boolean exitFamily(PersonDto currentPersonDto) {
		return false;
	}

	@Override
	public void delete(FamilyDto familyDto, PersonDto currentPersonDto) {
		Family family = familyMapper.mapDtoToModelLight(familyDto);
		Person person = personMapper.mapDtoToModel(currentPersonDto);

		log.info("Удаление семейной группы с ID: '{}' пользователя с ID: '{}'",
				 family.getFamilyId(),
				 person.getPersonId());

		Family familyToDelete = findById(
				family.getFamilyId(),
				person.getPersonId()).orElseThrow(() -> {
			log.warn("Не удалось удалить семейную группу с ID '{}' - семейная группа не найдена", family.getFamilyId());
			return new IllegalArgumentException(NOT_FOUND_FAMILY_MESSAGE);
		});

		try {
			familyRepository.delete(familyToDelete.getFamilyId(), person.getPersonId());
			log.info("Данные семейной группы с ID '{}' удалены", familyToDelete.getFamilyId());
		} catch (RuntimeException e) {
			log.error("Ошибка при удалении данных семейной группы с ID '{}': '{}'",
					  familyToDelete.getFamilyId(),
					  e.getMessage(),
					  e);
			throw e;
		}
	}
}
