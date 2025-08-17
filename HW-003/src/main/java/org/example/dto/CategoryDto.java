package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.CategoryType;
import org.example.model.Person;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CategoryDto {
	private UUID categoryId;
	private String categoryName;
	private CategoryType type;
	private Person person;

	@Override
	public String toString() {
		return categoryName;
	}
}
