package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
		name = "person",
		uniqueConstraints = {
				@UniqueConstraint(
						name = "unique_person_user_name_key",
						columnNames = "user_name"),
				@UniqueConstraint(
						name = "unique_person_email_key",
						columnNames = "email")
		}
)
public class Person {

	@Id
	@Column(name = "person_id", nullable = false, updatable = false)
	private UUID personId;

	@Column(name = "user_name", nullable = false, unique = true, length = 100)
	private String userName;

	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;

	@Column(name = "email", nullable = false, unique = true, length = 100)
	private String email;

	@ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
	@Builder.Default
	private List<Family> families = new ArrayList<>();

	@Column(name = "create_date", insertable = false, updatable = false)
	@Generated(GenerationTime.INSERT)
	private LocalDateTime createDate;
}
