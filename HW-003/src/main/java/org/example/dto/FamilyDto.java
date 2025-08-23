package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Person;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamilyDto {
	private UUID familyId;
	private String name;
	private Person creator;
	private List<Person> members;

	@Override
	public String toString() {
		return name;
	}
}