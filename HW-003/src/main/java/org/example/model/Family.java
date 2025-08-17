package org.example.model;

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
public class Family {
	private UUID familyId;
	private String familyName;
	private Person ownerPersonId;
	private List<Person> members;

	@Override
	public String toString() {
		return familyName;
	}
}