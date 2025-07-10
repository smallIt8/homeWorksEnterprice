package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Category {
    private UUID categoryId;
    private String categoryName;
    private TransactionType type;

    public Category(String categoryName, TransactionType type) {
        this.categoryName = categoryName;
        this.type = type;
    }
}