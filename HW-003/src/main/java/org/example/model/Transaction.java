package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.example.util.constant.ColorsConstant.INDIGO;
import static org.example.util.constant.ColorsConstant.RESET;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Transaction {
    private UUID transactionId;
    private String transactionName;
    private TransactionType type;
    private BigDecimal amount;
    private Category category;
    private Person person;
    private LocalDate createDate;

    public Transaction(UUID transactionId, String transactionName, TransactionType type, BigDecimal amount, Category category, Person person) {
        this.transactionId = transactionId;
        this.transactionName = transactionName;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.person = person;
    }

    @Override
    public String toString() {
        return "Название затрат: " + INDIGO + transactionName + RESET + "\n" +
                "Тип операции: " + INDIGO + type + RESET + "\n" +
                "Количество/Стоимость: " + INDIGO + amount + RESET + "\n" +
                "Категория: " + INDIGO + category + RESET + "\n" +
                "Имя добавившего: " + INDIGO + person + RESET + "\n" +
                "Категория: " + INDIGO + category + RESET + "\n";
    }
}