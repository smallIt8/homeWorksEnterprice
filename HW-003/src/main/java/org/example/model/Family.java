package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Family {
	private UUID familyId;
	private String familyName;

	public Family(UUID familyId) {
		this.familyId = familyId;
	}

	@Override
	public String toString() {
		return familyName;
	}
}