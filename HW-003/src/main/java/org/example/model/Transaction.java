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
		name = "transaction",
		indexes = @Index(
				name = "transaction_type_index",
				columnList = "type"
		)
)
public class Transaction {

	@Id
	@Column(name = "transaction_id", nullable = false, updatable = false)
	private UUID transactionId;

	@Column(name = "transaction_name", nullable = false, length = 100)
	private String transactionName;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false, length = 20)
	private TransactionType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "category_id",
			nullable = false,
			foreignKey = @ForeignKey(name = "transaction_category_category_id_fk")
	)
	private Category category;

	@Column(name = "amount", nullable = false, precision = 10, scale = 2)
	private BigDecimal amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "person_id",
			nullable = false,
			foreignKey = @ForeignKey(name = "transaction_person_person_id_fk")
	)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Person creator;

	@Column(name = "transaction_date", nullable = false)
	private LocalDate transactionDate;

	@Column(name = "create_date", insertable = false, updatable = false)
	@Generated(GenerationTime.INSERT)
	private LocalDateTime createDate;
}
