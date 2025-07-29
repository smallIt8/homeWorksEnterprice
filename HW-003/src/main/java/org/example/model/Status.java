package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public enum Status {
	ACTIVE("Активная"),
	COMPLETED("Завершенная"),
	EXPIRED("Истекшая"),
	;

	private final String statusName;

	@Override
	public String toString() {
		return statusName;
	}
}