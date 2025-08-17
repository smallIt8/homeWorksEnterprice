package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.CategoryType;
import org.example.model.Person;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
