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
	private String name;
	private CategoryType type;
	private Person creator;

	@Override
	public String toString() {
		return name;
	}
}
