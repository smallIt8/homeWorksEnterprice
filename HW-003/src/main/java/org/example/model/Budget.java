package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.util.DateConverterUtil;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
		name = "budget",
		uniqueConstraints = @UniqueConstraint(
				name = "unique_budget_per_category_per_period_per_person_key",
				columnNames = {"category_id", "period", "person_id"}
		),
		indexes = @Index(
				name = "budget_person_index",
				columnList = "person_id"
		)
)
public class Budget {

	@Id
	@Column(name = "budget_id")
	private UUID budgetId;

	@Column(name = "budget_name", nullable = false)
	private String budgetName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Column(name = "budget_limit", nullable = false)
	private BigDecimal limit;

	@Convert(converter = DateConverterUtil.class)
	@Column(name = "period", nullable = false)
	private YearMonth period;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", nullable = false)
	private Person creator;
}
