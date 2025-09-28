package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
		name = "category",
		uniqueConstraints = @UniqueConstraint(
				name = "unique_category_per_category_name_per_person_key",
				columnNames = {"category_name", "person_id"}
		),
		indexes = @Index(
				name = "category_type_index",
				columnList = "type")
)
public class Category {

	@Id
	@Column(name = "category_id", nullable = false, updatable = false)
	private UUID categoryId;

	@Column(name = "category_name", nullable = false, length = 100)
	private String categoryName;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false, length = 20)
	private CategoryType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "person_id",
			nullable = false,
			foreignKey = @ForeignKey(name = "category_person_id_fk")
	)
	private Person creator;

	@Override
	public String toString() {
		return categoryName;
	}
}
