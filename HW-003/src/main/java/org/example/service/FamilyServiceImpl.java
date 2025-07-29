package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Family;
import org.example.model.Person;
import org.example.repository.FamilyRepository;
import org.example.util.AppUtil;
import org.example.util.constant.RegexConstant;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import static org.example.util.constant.ErrorMessageConstant.ERROR_ENTER_FIRST_NAME_MESSAGE;
import static org.example.util.constant.MenuConstant.*;

@Slf4j
@RequiredArgsConstructor

public class FamilyServiceImpl implements FamilyService {
	private final FamilyRepository familyRepository;
	private static final Scanner SCANNER = new Scanner(System.in);
	private String familyName;

	@Override
	public void create() {
	}

	@Override
	public void create(Person currentPerson) {
		log.info("Создание новой семейной группы");
		System.out.println(CREATION_FAMILY_MESSAGE);
		createFamilyName();
		Family family = new Family(
				UUID.randomUUID(),
				familyName.toUpperCase(),
				currentPerson
		);
		try {
			familyRepository.create(family);
			log.info("Семейная группа с ID: '{}' успешно создана", family.getFamilyId());
			System.out.println(CREATED_FAMILY_MESSAGE);
		} catch (RuntimeException e) {
			log.error("Ошибка при создании семейной группы: '{}'", e.getMessage(), e);
			throw e;
		}
	}

	private void createFamilyName() {
		AppUtil.loopIterationAndExit(count -> {
			log.info("Создание имени семейной группы");
			System.out.print(ENTER_FAMILY_NAME);
			familyName = SCANNER.nextLine();
			if (familyName.matches(RegexConstant.FAMILY_NAME_REGEX)) {
				log.info("Имя семейной группы успешно создано");
				return true;
			} else {
				if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
					System.out.println(ERROR_ENTER_FIRST_NAME_MESSAGE);
				} else {
					log.error("Ошибка при создании имени семейной группы: '{}'", familyName);
				}
				return false;
			}
		}, AppUtil.MAX_ITERATION_LOOP);
	}

	@Override
	public void createBatch() {

	}

	@Override
	public Optional<Family> joinFamily(Person person, Family family) {
		return Optional.empty();
	}

	@Override
	public boolean addMember(String email, UUID person) {
		return false;
	}

	@Override
	public List<Family> getAll() {
		return List.of();
	}

	@Override
	public List<Family> getAllByOwner(Person currentPerson) {
		List<Family> families = familyRepository.getAllByOwner(currentPerson);
		if (families.isEmpty()) {
			log.warn("Список семейных групп, созданных текущим пользователем пуст");
			System.out.println(EMPTY_LIST_FAMILY_BY_PERSON_MESSAGE);
		} else {
			log.info("Получено семейных групп, созданных текущим пользователем: '{}' ", families.size());

		}
		return families;
	}

	@Override
	public Optional<Family> update(Person currentPerson) {
		log.info("Обновление имени семейных групп созданных текущим пользователем");
		List<Family> families = getAllByOwner(currentPerson);
		if (families.isEmpty()) {
			log.warn("Не удалось найти ни одной семейной группы, созданной текущим пользователем");
			System.out.println("Создать");
			log.info("Перенаправление в меню создания семейной группы");
			create(currentPerson);
			return Optional.empty();
		}
		Optional<Family> selectFamily = AppUtil.selectFromList(
				families,
				LIST_FAMILY_BY_PERSON_MESSAGE
		);
		selectFamily.ifPresent(familyUpdate -> {
			log.info("Выбранная семейная группа: '{}'", familyUpdate.getFamilyName());
			createFamilyName();
			familyUpdate.setFamilyName(familyName.toUpperCase());
			try {
				familyRepository.update(familyUpdate);
				log.info("Имя выбранной семейной группы '{}' успешно обновлено", familyUpdate.getFamilyName());
				System.out.println(UPDATED_FAMILY_MESSAGE);
			} catch (RuntimeException e) {
				log.error("Ошибка при обновлении имени выбранной семейной группы'{}': '{}'", familyUpdate.getFamilyName(), e.getMessage(), e);
				throw e;
			}
		});
		return selectFamily;
	}

	@Override
	public boolean exitFamily(Person person) {
		return false;
	}

	@Override
	public void delete(UUID value) {

	}
}