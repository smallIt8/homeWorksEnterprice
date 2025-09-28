package org.example.model;

import jakarta.persistence.*;
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
@Entity
@Table(
		name = "family",
		indexes = @Index(
				name = "family_person_index",
				columnList = "person_id"
		)
)
public class Family {

	@Id
	@Column(name = "family_id")
	private UUID familyId;

	@Column(name = "family_name", nullable = false, unique = true)
	private String familyName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", nullable = false)
	private Person creator;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "family_person",
			joinColumns = @JoinColumn(name = "family_id"),
			inverseJoinColumns = @JoinColumn(name = "person_id")
	)
	private List<Person> members;
}
