package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	@Column(name = "financial_goal_id")
	private UUID financialGoalId;

	@Column(name = "financial_goal_name", nullable = false)
	private String financialGoalName;

	@Column(name = "target_amount", nullable = false)
	private BigDecimal targetAmount;

	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", nullable = false)
	private Person creator;

	@Column(name = "create_date", insertable = false)
	private LocalDateTime createDate;
}
