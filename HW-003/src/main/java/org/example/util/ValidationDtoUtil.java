package org.example.util;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@UtilityClass
public class ValidationDtoUtil {

	private static final Validator validator;
	private static final ValidatorFactory validationFactory;

	static {
		try {
			validationFactory = Validation.buildDefaultValidatorFactory();
			validator = validationFactory.getValidator();
			log.info("ValidatorFactory и Validator успешно инициализированы");
		} catch (Exception e) {
			log.error("Ошибка при инициализации ValidatorFactory или Validator: '{}'",
					  e.getMessage(),
					  e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static <T> Map<String, List<String>> validateAnnotation(T dto) {
		Set<ConstraintViolation<T>> violations = validator.validate(dto);
		if (!violations.isEmpty()) {
			Map<String, List<String>> warns = new LinkedHashMap<>();
			for (ConstraintViolation<T> violation : violations) {
				warns.computeIfAbsent(violation.getPropertyPath().toString(), k -> new ArrayList<>())
						.add(violation.getMessage());
				log.warn("Нарушение валидации поле: '{}', значение: '{}', ошибка: '{}'",
						 violation.getPropertyPath(),
						 violation.getInvalidValue(),
						 violation.getMessage());
			}
			String warnMessage = warns.toString();
			log.error("Валидация объекта класса '{}' завершилась с ошибкой: {}",
					  dto.getClass().getSimpleName(),
					  warnMessage);
			return warns;
		}
		log.info("Валидация объекта класса '{}' успешно пройдена",
				 dto.getClass().getSimpleName());
		return Collections.emptyMap();
	}
}
