package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.CategoryType;

import java.util.UUID;

import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.RegexConstant.CATEGORY_NAME_REGEX;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

	private UUID categoryId;

	@NotBlank(message = WARNING_CATEGORY_NAME_NOT_NULL_MESSAGE)
	@Pattern(regexp = CATEGORY_NAME_REGEX,
			message = WARNING_ENTER_CATEGORY_NAME_MESSAGE
	)
	private String categoryName;

	private CategoryType type;

	private PersonDto creatorDto;
}
