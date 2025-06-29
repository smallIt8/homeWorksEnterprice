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

public class Person {
    private UUID personId;
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal salary;
    private String department;
    private LocalDate createDate;

    public Person(UUID personId, String firstName, String lastName, String email, BigDecimal salary, String department) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salary = salary;
        this.department = department;
    }

    @Override
    public String toString() {
        return "Имя: " + INDIGO + firstName + RESET + "\n" +
                "Фамилия: " + INDIGO + lastName + RESET + "\n" +
                "Эмейл: " + INDIGO + email + RESET + "\n" +
                "Зарплата: " + INDIGO + salary + RESET + "\n" +
                "Отдел: " + INDIGO + department + RESET + "\n";
    }
}