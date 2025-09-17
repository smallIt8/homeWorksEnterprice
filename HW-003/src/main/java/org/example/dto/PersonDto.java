package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

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
	private List<FamilyDto> familiesDto;

	public String getFullName() {
		return firstName + " " + lastName + "\n";
	}
}
