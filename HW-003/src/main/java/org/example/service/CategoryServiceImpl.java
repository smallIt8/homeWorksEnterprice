package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.CategoryDto;
import org.example.dto.PersonDto;
import org.example.mapper.CategoryMapper;
import org.example.mapper.PersonMapper;
import org.example.model.Category;
import org.example.model.Person;
import org.example.repository.CategoryRepository;

import java.util.*;

import static org.example.util.ValidationDtoUtil.validateAnnotation;
import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.InfoMessageConstant.*;

@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	private final PersonMapper personMapper;

	@Override
	public void create(CategoryDto categoryDto) {
		log.info("Добавление новой категории пользователя с ID: '{}'", categoryDto.getCreatorDto().getPersonId());
		CategoryDto inputCategory = buildCategory(categoryDto);

		Category category = categoryMapper.mapDtoToModel(inputCategory);
		log.info("Создана подготовленная модель добавляемой категории '{}' пользователя: '{}'",
				 category.getCategoryName(),
				 category.getCreator().getPersonId());
		try {
			categoryRepository.create(category);
			log.info("Категория с ID: '{}' успешно создана для пользователя c ID: '{}'",
					 category.getCategoryId(),
					 category.getCreator().getPersonId());
		} catch (RuntimeException e) {
			log.error("Ошибка при создании категории: '{}'",
					  e.getMessage(),
					  e);
			throw e;
		}
	}

	@Override
	public Map<CategoryDto, String> createBatch(List<CategoryDto> categoriesDto) {
		log.info("Добавление пакета категорий: количество = '{}'", categoriesDto.size());
		Map<CategoryDto, String> errors = new LinkedHashMap<>();
		List<Category> categoriesSuccess = new ArrayList<>();

		for (CategoryDto categoryDto : categoriesDto) {
			try {
				CategoryDto validDto = buildCategory(categoryDto);
				categoriesSuccess.add(categoryMapper.mapDtoToModel(validDto));
				log.info("Категория с ID: '{}' успешно подготовлена для добавления", validDto.getCategoryId());
			} catch (RuntimeException e) {
				log.warn("Ошибка при создании категории '{}' в пакете: '{}'",
						 categoryDto.getCategoryName(),
						 e.getMessage());
				errors.put(categoryDto, e.getMessage());
			}
		}
		if (!categoriesSuccess.isEmpty()) {
			categoryRepository.createBatch(categoriesSuccess);
			log.info("Успешно добавленные категории: '{}'", categoriesSuccess.size());
		}
		log.info("Пакет категорий обработан, успешно: '{}',  количество ошибок: '{}'",
				 categoriesSuccess.size(),
				 errors.size());
		return errors;
	}

	private CategoryDto buildCategory(CategoryDto categoryDto) {
		validateAnnotation(categoryDto);

		return CategoryDto.builder()
				.categoryId(UUID.randomUUID())
				.categoryName(categoryDto.getCategoryName())
				.type(categoryDto.getType())
				.creatorDto(categoryDto.getCreatorDto())
				.build();
	}

	@Override
	public Optional<Category> findById(UUID categoryId, UUID currentPersonId) {
		log.info("Получение данных категории по ID: '{}'", categoryId);
		try {
			Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
			if (categoryOpt.isEmpty()) {
				log.warn("Категория с ID: '{}' не найдена", categoryId);
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
					  e.getMessage(),
					  e);
			throw e;
		}
	}

	@Override
	public List<CategoryDto> findAll(PersonDto currentPersonDto) {
		log.info("Получение списка категорий пользователя с ID: '{}'", currentPersonDto.getPersonId());
		try {
			List<Category> categories = categoryRepository.findAll(currentPersonDto.getPersonId());
			if (categories.isEmpty()) {
				log.info("Список категорий пользователя с ID '{}' пуст", currentPersonDto.getPersonId());
				throw new IllegalArgumentException(EMPTY_LIST_CATEGORY_BY_PERSON_MESSAGE);
			}
			log.info("Получено '{}' категорий пользователя с ID '{}':",
					 categories.size(),
					 currentPersonDto.getPersonId());
			return categoryMapper.mapModelToDtoList(categories);
		} catch (RuntimeException e) {
			log.error("Ошибка при получении списка категорий пользователя '{}': '{}'",
					  currentPersonDto.getPersonId(),
					  e.getMessage(),
					  e);
			throw e;
		}
	}

	@Override
	public Optional<CategoryDto> update(CategoryDto categoryDto, PersonDto currentPersonDto) {
		Category category = categoryMapper.mapDtoToModel(categoryDto);
		Person person = personMapper.mapDtoToModel(currentPersonDto);

		log.info("Обновление категории с ID: '{}' пользователя с ID: '{}'",
				 category.getCategoryId(),
				 person.getPersonId());

		Category categoryUpdate = findById(
				category.getCategoryId(),
				person.getPersonId()).orElseThrow(() -> {
			log.warn("Не удалось обновить категорию с ID '{}' - категория не найдена", category.getCategoryId());
			return new IllegalArgumentException(NOT_FOUND_CATEGORY_MESSAGE);
		});

		log.info("Обновление данных категории с ID: '{}' пользователя с ID: '{}'",
				 categoryUpdate.getCategoryId(),
				 person.getPersonId());

		validateAnnotation(categoryDto);

		categoryUpdate.setCategoryName(category.getCategoryName());

		log.info("Создана подготовленная модель обновляемой категории с ID: '{}'", categoryUpdate.getCategoryId());
		try {
			categoryRepository.update(categoryUpdate);
			log.info("Категория с ID '{}' успешно обновлена", categoryUpdate.getCategoryId());
			return Optional.of(categoryMapper.mapModelToDto(categoryUpdate));
		} catch (RuntimeException e) {
			log.error("Ошибка при обновлении категории по ID '{}': '{}'",
					  categoryUpdate.getCategoryId(),
					  e.getMessage(),
					  e);
			throw e;
		}
	}

	@Override
	public void delete(CategoryDto categoryDto, PersonDto currentPersonDto) {
		Category category = categoryMapper.mapDtoToModelLight(categoryDto);
		Person person = personMapper.mapDtoToModel(currentPersonDto);

		log.info("Удаление категории с ID: '{}' пользователя с ID: '{}'",
				 category.getCategoryId(),
				 person.getPersonId());

		Category categoryToDelete = findById(
				category.getCategoryId(),
				person.getPersonId()).orElseThrow(() -> {
			log.warn("Не удалось удалить категорию с ID '{}' - категория не найдена", category.getCategoryId());
			return new IllegalArgumentException(NOT_FOUND_CATEGORY_MESSAGE);
		});

		try {
			categoryRepository.delete(categoryToDelete.getCategoryId(), person.getPersonId());
			log.info("Данные категории с ID '{}' удалены", categoryToDelete.getCategoryId());
		} catch (RuntimeException e) {
			log.error("Ошибка при удалении данных категории с ID '{}': '{}'",
					  categoryToDelete.getCategoryId(),
					  e.getMessage(),
					  e);
			throw e;
		}
	}
}
