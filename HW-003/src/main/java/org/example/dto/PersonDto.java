package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Family;

import java.util.UUID;

import static org.example.util.constant.ColorsConstant.INDIGO;
import static org.example.util.constant.ColorsConstant.RESET;

@Data
@NoArgsConstructor
public class PersonDto {
	private UUID personId;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private Family family;

	public PersonDto(UUID personId) {
		this.personId = personId;
	}

	public PersonDto(UUID personId, String userName, String password, String firstName, String lastName, String email) {
		this.personId = personId;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public PersonDto(String userName, String password, String firstName, String lastName, String email) {
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public PersonDto(UUID personId, String userName, String firstName, String lastName, String email, Family family) {
		this.personId = personId;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.family = family;
	}

	public PersonDto(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public PersonDto(UUID personId, String firstName, String lastName, String email) {
		this.personId = personId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public PersonDto(UUID personId, String password) {
		this.personId = personId;
		this.password = password;
	}

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