package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Family {
	private UUID familyId;
	private String familyName;
	private Person ownerPersonId;
	private List<Person> members;

	public Family(UUID familyId) {
		this.familyId = familyId;
	}

	public Family(UUID familyId, String familyName, Person ownerPersonId) {
		this.familyId = familyId;
		this.familyName = familyName;
		this.ownerPersonId = ownerPersonId;
	}

	@Override
	public String toString() {
		return familyName;
	}
}