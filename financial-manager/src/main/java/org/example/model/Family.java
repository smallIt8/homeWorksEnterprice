package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
		name = "family",
		uniqueConstraints = @UniqueConstraint(
				name = "unique_family_name_key",
				columnNames = "family_name"
		),
		indexes = @Index(
				name = "family_person_index",
				columnList = "person_id"
		)
)
public class Family {

	@Id
	@Column(name = "family_id", nullable = false, updatable = false)
	private UUID familyId;

	@Column(name = "family_name", nullable = false, unique = true, length = 100)
	private String familyName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "person_id",
			nullable = false,
			foreignKey = @ForeignKey(name = "family_person_person_id_fk")
	)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Person creator;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "family_person",
			joinColumns = @JoinColumn(
					name = "family_id",
					foreignKey = @ForeignKey(name = "family_person_family_family_id_fk")
			),
			inverseJoinColumns = @JoinColumn
					(name = "person_id",
							foreignKey = @ForeignKey(name = "family_person_person_person_id_fk")
					)
	)
	@Builder.Default
	private List<Person> members = new ArrayList<>();
}
