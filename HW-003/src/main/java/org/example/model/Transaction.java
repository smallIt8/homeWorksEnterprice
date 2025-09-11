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
		name = "transaction",
		indexes = @Index(
				name = "transaction_type_index",
				columnList = "type"
		)
)
public class Transaction {

	@Id
	private UUID transactionId;

	@Column(name = "transaction_name", nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private TransactionType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Column(name = "amount", nullable = false)
	private BigDecimal amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", nullable = false)
	private Person creator;

	@Column(name = "transaction_date", nullable = false)
	private LocalDate transactionDate;

	@Column(name = "create_date")
	private LocalDateTime createDate;
}