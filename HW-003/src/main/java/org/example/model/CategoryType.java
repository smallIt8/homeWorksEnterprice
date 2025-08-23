package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryType {
	INCOME("Приходная"),
	EXPENSE("Расходная");

	private final String typeName;

	@Override
	public String toString() {
		return typeName;
	}
}