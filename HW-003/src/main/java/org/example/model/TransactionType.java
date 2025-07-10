package org.example.model;

import lombok.Getter;

@Getter

public enum TransactionType {
    INCOME("Приход"),
    EXPENSE("Расход");

    private final String typeName;

    TransactionType(String typeName) {
        this.typeName = typeName;
    }
}