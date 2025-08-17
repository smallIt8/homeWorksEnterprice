package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.example.util.constant.ColorsConstant.INDIGO;
import static org.example.util.constant.ColorsConstant.RESET;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
	private UUID personId;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private Family family;
	private LocalDateTime createDate;

	@Override
	public String toString() {
		return "Имя: " + INDIGO + firstName + RESET + "\n" +
				"Фамилия: " + INDIGO + lastName + RESET + "\n" +
				"Семейная группа: " + INDIGO + (family == null ? "не состоит" : family.toString()) + RESET + "\n" +
				"Эмейл: " + INDIGO + email + RESET + "\n";
	}

	public String toNameString() {
		return INDIGO + firstName + " " + lastName + RESET + "\n";
	}
}