package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
	private UUID categoryId;
	private String categoryName;
	private CategoryType type;
	private Person person;

	@Override
	public String toString() {
		return categoryName;
	}
}