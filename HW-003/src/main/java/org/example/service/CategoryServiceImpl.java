package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PersonDto;
import org.example.dto.CategoryDto;
import org.example.mapper.PersonMapper;
import org.example.model.Category;
import org.example.model.CategoryType;
import org.example.model.Person;
import org.example.repository.CategoryRepository;

import java.util.*;

import static org.example.mapper.PersonMapper.*;
import static org.example.mapper.CategoryMapper.*;
import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.InfoMessageConstant.*;
import static org.example.util.constant.RegexConstant.*;

@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Override
	public void create(CategoryDto categoryDto) {
		Category category = dtoToModel(categoryDto);

		log.info("Добавление новой категории пользователя с ID: '{}'",
				 category.getCreator().getPersonId());

		String name = validateName(category.getName());
		CategoryType type = validateType(category.getType());

		Category inputCategory = Category.builder()
				.categoryId(UUID.randomUUID())
				.name(name.toLowerCase())
				.type(type)
				.creator(category.getCreator())
				.build();

		log.info("Создана подготовленная модель добавляемой категории '{}' пользователя: '{}'",
				 name,
				 category.getCreator().getPersonId());
		try {
			categoryRepository.create(inputCategory);
			log.info("Категория с ID: '{}' успешно создана для пользователя c ID: '{}'",
					 inputCategory.getCategoryId(),
					 category.getCreator().getPersonId());
		} catch (RuntimeException e) {
			log.error("Ошибка при создании категории: '{}'",
					  e.getMessage(), e);
			throw e;
		}
	}

	private String validateName(String name) {
		log.info("Создание имени категории");
		if (name.matches(CATEGORY_NAME_REGEX)) {
			log.info("Имя категории успешно создано: '{}'",
					 name);
			return name;
		} else {
			log.error("Неверный формат имени: '{}'",
					  name);
			throw new IllegalArgumentException(ERROR_ENTER_CATEGORY_NAME_MESSAGE);
		}
	}

	private CategoryType validateType(CategoryType type) {
		log.info("Проверка типа категории");
		if (type == null) {
			log.error("Тип категории не может быть пустым");
			throw new IllegalArgumentException(ERROR_SELECT_CATEGORY_TYPE_MESSAGE);
		}
		log.info("Тип категории успешно проверен: '{}'",
				 type);
		return type;
	}

	@Override
	public Map<CategoryDto, String> createBatch(List<CategoryDto> categoriesDto) {
		log.info("Добавление пакета категорий: количество = '{}'",
				 categoriesDto.size());

		Map<CategoryDto, String> errors = new LinkedHashMap<>();

		for (CategoryDto dto : categoriesDto) {
			try {
				create(dto);
			} catch (RuntimeException e) {
				log.warn("Ошибка при создании категории '{}' в пакете: '{}'",
						 dto.getName(),
						 e.getMessage());
				errors.put(dto, e.getMessage());
			}
		}
		log.info("Пакет категорий обработан, количество ошибок: {}",
				 errors.size());
		return errors;
		// Not implements List entity to repository
	}

	@Override
	public Optional<Category> findById(UUID categoryId, UUID currentPersonId) {
		log.info("Получение данных категории по ID: '{}'",
				 categoryId);
		try {
			Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
			if (categoryOpt.isEmpty()) {
				log.warn("Категория с ID: '{}' не найдена",
						 categoryId);
				throw new IllegalArgumentException(NOT_FOUND_CATEGORY_MESSAGE);
			}
			if (!categoryOpt.get().getCreator().getPersonId().equals(currentPersonId)) {
				log.warn("Категория с ID: '{}' не принадлежит пользователю с ID: '{}'",
						 categoryId,
						 currentPersonId);
				throw new SecurityException(ERROR_ACCESS_CATEGORY_MESSAGE);
			}
			return categoryOpt;
		} catch (RuntimeException e) {
			log.error("Ошибка при получении данных категории по ID '{}': '{}'",
					  categoryId,
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<CategoryDto> findAll(PersonDto currentPersonDto) {
		log.info("Получение списка категорий пользователя с ID: '{}'",
				 currentPersonDto.getPersonId());
		try {
			List<Category> categories = categoryRepository.findAll(currentPersonDto.getPersonId());
			if (categories.isEmpty()) {
				log.info("Список категорий пользователя с ID '{}' пуст",
						 currentPersonDto.getPersonId());
				throw new IllegalArgumentException(EMPTY_LIST_CATEGORY_BY_PERSON_MESSAGE);
			}
			log.info("Получено '{}' категорий пользователя с ID '{}':",
					 categories.size(),
					 currentPersonDto.getPersonId());
			return modelToDtoList(categories);
		} catch (RuntimeException e) {
			log.error("Ошибка при получении списка категорий пользователя '{}': '{}'",
					  currentPersonDto.getPersonId(),
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Optional<CategoryDto> update(CategoryDto categoryDto, PersonDto currentPersonDto) {
		Category category = dtoToModel(categoryDto);
		Person person = dtoToModel(currentPersonDto);

		log.info("Обновление категории с ID: '{}' пользователя с ID: '{}'",
				 category.getCategoryId(),
				 person.getPersonId());

		Category categoryUpdate = findById(
				category.getCategoryId(),
				person.getPersonId()).orElseThrow(() -> {
			log.warn("Не удалось обновить категорию с ID '{}' - категория не найдена",
					 category.getCategoryId());
			return new IllegalArgumentException(NOT_FOUND_CATEGORY_MESSAGE);
		});

		log.info("Обновление данных категории с ID: '{}' пользователя с ID: '{}'",
				 categoryUpdate.getCategoryId(),
				 person.getPersonId());

		String name = validateName(category.getName());
		CategoryType type = validateType(category.getType());

		categoryUpdate.setName(name);
		categoryUpdate.setType(type);

		log.info("Создана подготовленная модель обновляемой категории с ID: '{}'",
				 categoryUpdate.getCategoryId());
		try {
			categoryRepository.update(categoryUpdate);
			log.info("Категория с ID '{}' успешно обновлена",
					 categoryUpdate.getCategoryId());
			return Optional.of(modelToDto(categoryUpdate));
		} catch (RuntimeException e) {
			log.error("Ошибка при обновлении категории по ID '{}': '{}'",
					  categoryUpdate.getCategoryId(),
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void delete(CategoryDto categoryDto, PersonDto currentPersonDto) {
		Category category = dtoToModel(categoryDto);
		Person person = PersonMapper.dtoToModel(currentPersonDto);

		log.info("Удаление категории с ID: '{}' пользователя с ID: '{}'",
				 category.getCategoryId(),
				 person.getPersonId());

		Category categoryToDelete = findById(
				category.getCategoryId(),
				person.getPersonId()).orElseThrow(() -> {
			log.warn("Не удалось удалить категорию с ID '{}' - категория не найдена",
					 category.getCategoryId());
			return new IllegalArgumentException(NOT_FOUND_CATEGORY_MESSAGE);
		});

		try {
			categoryRepository.delete(categoryToDelete.getCategoryId(), person.getPersonId());
			log.info("Данные категории с ID '{}' удалены",
					 categoryToDelete.getCategoryId());
		} catch (RuntimeException e) {
			log.error("Ошибка при удалении данных категории с ID '{}': '{}'",
					  categoryToDelete.getCategoryId(),
					  e.getMessage(), e);
			throw e;
		}
	}
}