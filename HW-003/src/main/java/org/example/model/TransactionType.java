package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public enum TransactionType {
	INCOME("Приход"),
	EXPENSE("Расход");

	private final String typeName;

	@Override
	public String toString() {
		return typeName;
	}
}