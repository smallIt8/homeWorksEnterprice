package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
		name = "financial_goal",
		uniqueConstraints = @UniqueConstraint(
				name = "unique_financial_goal_per_financial_goal_name_per_person_key",
				columnNames = {"financial_goal_name", "person_id"}
		),
		indexes = @Index(
				name = "financial_goal_person_index",
				columnList = "person_id"
		)
)
public class FinancialGoal {

	@Id
	@Column(name = "financial_goal_id", nullable = false, updatable = false)
	private UUID financialGoalId;

	@Column(name = "financial_goal_name", nullable = false, length = 100)
	private String financialGoalName;

	@Column(name = "target_amount", nullable = false, precision = 10, scale = 2)
	private BigDecimal targetAmount;

	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "person_id",
			nullable = false,
			foreignKey = @ForeignKey(name = "financial_goal_person_person_id_fk")
	)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Person creator;

	@Column(name = "create_date", insertable = false, updatable = false)
	@Generated(GenerationTime.INSERT)
	private LocalDateTime createDate;
}
