package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Family;

import java.util.UUID;

import static org.example.util.constant.ColorsConstant.INDIGO;
import static org.example.util.constant.ColorsConstant.RESET;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
	private UUID personId;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private Family family;

	@Override
	public String toString() {
		return "Имя: " + INDIGO + firstName + RESET + "\n" +
				"Фамилия: " + INDIGO + lastName + RESET + "\n" +
				"Семейная группа: " + INDIGO + (family == null ? "не состоит" : family.toString()) + RESET + "\n" +
				"Эмейл: " + INDIGO + email + RESET + "\n";
	}

	public String toNameString() {
		return firstName + " " + lastName + "\n";
	}
}