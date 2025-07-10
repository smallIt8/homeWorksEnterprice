package org.example.repository;

import org.example.model.Person;

import java.util.List;
import java.util.UUID;

public interface PersonRepository extends Repository<Person, UUID> {
    void createBatch(List<Person> persons);

    boolean checkEmail(String email);

    boolean checkEmail(String email, UUID currentPersonId);

    List<Person> getByLastName(String lastName);

    List<Person> getAllBySalary();

    List<Person> getAllByCreateDate();
}