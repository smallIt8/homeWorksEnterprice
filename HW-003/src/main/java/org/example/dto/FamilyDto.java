package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Person;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class FamilyDto {
	private UUID familyId;
	private String familyName;
	private Person ownerPersonId;
	private List<Person> members;

	public FamilyDto(UUID familyId) {
		this.familyId = familyId;
	}

	public FamilyDto(UUID familyId, String familyName, Person ownerPersonId) {
		this.familyId = familyId;
		this.familyName = familyName;
		this.ownerPersonId = ownerPersonId;
	}

	@Override
	public String toString() {
		return familyName;
	}
}
